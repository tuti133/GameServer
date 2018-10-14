package ptit.ltm.backend.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
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
import ptit.ltm.backend.util.Constant;

@Component
@ServerEndpoint(value = "/game", configurator = CustomSpringConfigurator.class)
public class GameController {

	static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

	private final String USER_ID = "USER_ID";
	private final String NICK_NAME = "NICK_NAME";

	@Autowired
	private Gson gson;

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
			userSession.getBasicRemote().sendText("System: you are connected with id " + x[0]);
		} else {
			SocketMessageDto request = gson.fromJson(message, SocketMessageDto.class);
			// user gửi lời thách đấu lên server chuyển hộ cho user khác
			if (request.getType().equals(Constant.CHALLENGE_REQUEST)) {
				sendChallengeMessage(request, userSession);
			}
			// user trả lời lời thách đấu nhận lời hay ko?
			else if (request.getType().equals(Constant.CHALLENGE_RESPONSE)) {
				if (request.getMsg().equals(Constant.REJECT)) {
					sendRejectMessage(request, userSession);
				} else {
					for (Session ss : users) {
						if (getIdFromSession(ss) == request.getId()) {
							SocketMessageDto dto = new SocketMessageDto();
							dto.setMsg("match");
							dto.setType(Constant.CHALLENGE_RESPONSE);
							ss.getBasicRemote().sendText(gson.toJson(dto));
							userSession.getBasicRemote().sendText(gson.toJson(dto));
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
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	private void sendChallengeMessage(SocketMessageDto request, Session userSession) throws IOException {
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

	private int getIdFromSession(Session ss) {
		return Integer.parseInt((String) ss.getUserProperties().get(USER_ID));
	}

	private String getNickNameFromSession(Session ss) {
		return (String) ss.getUserProperties().get(NICK_NAME);
	}

}
