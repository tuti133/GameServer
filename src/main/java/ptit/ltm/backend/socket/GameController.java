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
			for(Session ss : users) {
				if(ss.getUserProperties().get("userId").equals(message)) {
					ss.getBasicRemote().sendText("have match");
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
