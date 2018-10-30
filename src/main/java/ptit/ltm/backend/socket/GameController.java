package ptit.ltm.backend.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import ptit.ltm.backend.config.CustomSpringConfigurator;
import ptit.ltm.backend.entity.Match;
import ptit.ltm.backend.entity.MatchQuestion;
import ptit.ltm.backend.entity.Question;
import ptit.ltm.backend.entity.User;
import ptit.ltm.backend.entity.UserMatches;
import ptit.ltm.backend.repository.MatchQuestionRepository;
import ptit.ltm.backend.repository.QuestionRepository;
import ptit.ltm.backend.repository.UserMatchRepository;
import ptit.ltm.backend.repository.UserRepository;
import ptit.ltm.backend.service.MatchService;
import ptit.ltm.backend.util.Constant;

@Component
@ServerEndpoint(value = "/game", configurator = CustomSpringConfigurator.class)
public class GameController {

	private static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

	private static final String USER_ID = "USER_ID";
	private static final String NICK_NAME = "NICK_NAME";

	@Autowired
	private Gson gson;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private MatchService matchService;

	@Autowired
	private UserMatchRepository userMatchRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MatchQuestionRepository matchQuestionRepository;

	@OnOpen
	public void handleOpen(Session session) throws IOException {
		users.add(session);
	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		System.err.println(message);

		String nickName = getNickNameFromSession(userSession);

		// first message from user
		if (nickName == null) {
			String[] x = message.split(",");
			userSession.getUserProperties().put(USER_ID, x[0]);
			userSession.getUserProperties().put(NICK_NAME, x[1]);
		} else {
			SocketMessageDto request = gson.fromJson(message, SocketMessageDto.class);
			// user gửi lời thách đấu lên server chuyển hộ cho user khác
			if (request.getType().equals(Constant.CHALLENGE_REQUEST)) {
				sendChallengeMessage(request, userSession);
			} else if (request.getType().equals(Constant.QUIT)) {
				int idQuit = request.getId();
				int idWin = request.getIdWin();
				User userLose = userRepository.findById(idQuit).get();
				User userWin = userRepository.findById(idWin).get();
				userLose.setScore(userLose.getScore());
				userWin.setScore(userWin.getScore() + 1.0);
				userLose.setStatus(Constant.AVAILABLE_STATUS);
				userWin.setStatus(Constant.AVAILABLE_STATUS);
				userRepository.save(userLose);
				userRepository.save(userWin);
				List<UserMatches> list = userMatchRepository.findByMatchId(request.getMatchId());
				if (list.size() == 2) {
					if (list.get(0).getUserId() == idQuit) {
						sendResultMatchQuit(list.get(1), list.get(0));
					} else {
						sendResultMatchQuit(list.get(0), list.get(1));
					}
				}
				for (Session ss : users) {
					if (getIdFromSession(ss) == request.getIdWin()) {
						sendQuitMessage(ss, userSession, Constant.YOU_WIN);
					}
				}

			}
			
            // user roi tran dau luc game over
            else if (request.getType().equals(Constant.LEAVE_MATCH)) {
                for (Session session : users) {
                    System.err.println(getIdFromSession(session));
                    if(getIdFromSession(session) == request.getIdStay()) {
                        System.err.println("Send " + getIdFromSession(session));
                        SocketMessageDto dto = new SocketMessageDto();
                        dto.setType(Constant.LEAVE_MATCH);
                        dto.setMsg(Constant.LEAVE_MATCH);
                        session.getBasicRemote().sendText(gson.toJson(dto));
                    }
                }
            }
			
			// user trả lời lời thách đấu nhận lời hay ko?
			else if (request.getType().equals(Constant.CHALLENGE_RESPONSE)) {
				if (request.getMsg().equals(Constant.REJECT)) {
					sendRejectMessage(request, userSession);
				} else {
					for (Session ss : users) {
						if (getIdFromSession(ss) == request.getId()) {
							SocketMessageDto dto = new SocketMessageDto();
							dto.setType(Constant.CHALLENGE_RESPONSE);
							dto.setMsg(Constant.ACCEPT);
							List<ptit.ltm.backend.entity.Question> questions = questionRepository
									.findRandomQuestions(Constant.RANDOM_QUESTION_NUMBER);
							dto.setQuestionList(questions);
							Match match = matchService.create();
							dto.setMatchId(match.getId());
							for (Question question : questions) {
								MatchQuestion mq = new MatchQuestion();
								mq.setMatchId(match.getId());
								mq.setQuestionId(question.getId());
								matchQuestionRepository.save(mq);
							}
							ss.getBasicRemote().sendText(gson.toJson(dto));
							userSession.getBasicRemote().sendText(gson.toJson(dto));

							User user1 = userRepository.findById(getIdFromSession(ss)).get();
							User user2 = userRepository.findById(getIdFromSession(userSession)).get();
							user1.setStatus(Constant.BUSY_STATUS);
							user2.setStatus(Constant.BUSY_STATUS);
							userRepository.save(user1);
							userRepository.save(user2);

							UserMatches userMatches1 = new UserMatches();
							userMatches1.setMatchId(match.getId());
							userMatches1.setUserId(user1.getId());

							UserMatches userMatches2 = new UserMatches();
							userMatches2.setMatchId(match.getId());
							userMatches2.setUserId(user2.getId());

							userMatchRepository.save(userMatches1);
							userMatchRepository.save(userMatches2);

							break;
						}
					}
				}
			}
		}
	}

	@OnClose
	public void handleClose(Session session) {
		users.remove(session);
		User user = userRepository.findById(getIdFromSession(session)).get();
		user.setStatus(Constant.OFFLINE_STATUS);
		userRepository.save(user);
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	private void sendChallengeMessage(SocketMessageDto request, Session userSession) throws IOException {
		if (!userRepository.findById(request.getId()).get().getStatus().equals(Constant.AVAILABLE_STATUS)) {
			SocketMessageDto response = new SocketMessageDto();
			response.setType(Constant.PLAYER_UNAVAILABLE);
			userSession.getBasicRemote().sendText(gson.toJson(response));
			return;
		}
		for (Session user : users) {
			if (getIdFromSession(user) == request.getId()) {
				SocketMessageDto response = new SocketMessageDto();
				response.setType(Constant.CHALLENGE_REQUEST);
				response.setId(getIdFromSession(userSession));
				response.setNickName(getNickNameFromSession(userSession));
				user.getBasicRemote().sendText(gson.toJson(response));
				return;
			}
		}
	}

	public void sendResultMatchQuit(UserMatches userWin, UserMatches userLose) {
		userWin.setResult(Constant.WIN);
		userWin.setPoint(1.0);
		userLose.setResult(Constant.LOST);
		userLose.setPoint(0.0);
		userMatchRepository.save(userWin);
		userMatchRepository.save(userLose);
	}

	public static void sendResult(UserMatches player) throws IOException {
		for (Session user : users) {
			if (getIdFromSession(user) == player.getUserId()) {
				SocketMessageDto response = new SocketMessageDto();
				response.setType(Constant.RESULT_RESPONSE);
				response.setMsg(String.valueOf(player.getResult()));
				response.setCorrectAnswer(player.getCorrectAnswers());
				Gson gson = new Gson();
				user.getBasicRemote().sendText(gson.toJson(response));
				System.err.println(response.toString());
				return;
			}
		}
	}

	private void sendRejectMessage(SocketMessageDto request, Session userSession) throws IOException {
		for (Session user : users) {
			if (getIdFromSession(user) == request.getId()) {
				SocketMessageDto response = new SocketMessageDto();
				response.setType(Constant.CHALLENGE_RESPONSE);
				response.setId(getIdFromSession(userSession));
				response.setNickName(getNickNameFromSession(userSession));
				response.setMsg(Constant.REJECT);
				user.getBasicRemote().sendText(gson.toJson(response));
				return;
			}
		}
	}

	private static int getIdFromSession(Session ss) {
		return Integer.parseInt((String) ss.getUserProperties().get(USER_ID));
	}

	private static String getNickNameFromSession(Session ss) {
		return (String) ss.getUserProperties().get(NICK_NAME);
	}

	private void sendQuitMessage(Session session, Session userSession, String msg) throws IOException {
		SocketMessageDto response = new SocketMessageDto();
		response.setType(Constant.YOU_WIN);
		response.setNickName(getNickNameFromSession(userSession));
		response.setMsg(msg);
		session.getBasicRemote().sendText(gson.toJson(response));
	}

}
