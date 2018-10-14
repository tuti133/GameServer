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

import org.springframework.stereotype.Component;

import ptit.ltm.backend.config.CustomSpringConfigurator;
import ptit.ltm.backend.entity.Match;
import ptit.ltm.backend.util.Constant;

@Component
@ServerEndpoint(value = "/game", configurator = CustomSpringConfigurator.class)
public class GameController {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

	@OnOpen
	public void handleOpen(Session session) throws IOException {
		users.add(session);
	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		System.err.println(message);
		String userId = (String) userSession.getUserProperties().get("userId");
		if (userId == null) {
			userSession.getUserProperties().put("userId", message);
			userSession.getBasicRemote().sendText("System: you are connected as " + message);
		} else {
			for (Session ss : users) {
//				if(ss.getUserProperties().get("userId").equals(message)) {
				String idUserChallenged = ss.getUserProperties().get("userId").toString();
				if (message.endsWith(idUserChallenged) && message.contains(Constant.SEND_ATTACK_MSG)) {
					ss.getBasicRemote().sendText(userId + "/" + Constant.ATTACK_REQUEST_MSG + "/" + idUserChallenged);
					break;
				}
				if (message.contains(Constant.ACCEPT_MSG)) {
					
					//component of match : two userId, matchId
					
					String[] messageSplit = message.split("/");
					String idUserAccept = messageSplit[0];
					String idUserChallenge = messageSplit[2];
					Match match = new Match();
					match.setId(10);// set temporary matchId
					for (Session session : users) {
						if(session.getUserProperties().get("userId").toString().equals(idUserAccept)) {
							session.getBasicRemote().sendText("MacthID: "+match.getId() + ", UserId: "+idUserAccept );
						}
						if(session.getUserProperties().get("userId").toString().equals(idUserChallenge)) {
							session.getBasicRemote().sendText("MacthID: "+match.getId() + ", UserId: "+idUserChallenge );
						}
					}
					break;
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
}
