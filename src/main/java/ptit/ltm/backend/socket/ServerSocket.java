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

@ServerEndpoint(value = "/gameServer")
public class ServerSocket {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

	@OnOpen
	public void handleOpen(Session session) throws IOException {
		users.add(session);
		for (Session ss : users) {
			ss.getBasicRemote().sendText("UPDATE");
		}
	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		for (Session session : users) {
			session.getBasicRemote().sendText("UPDATE");
		}
	}
	
	@OnClose
	public void handleClose(Session session) throws IOException {
		users.remove(session);
		for (Session ss : users) {
			ss.getBasicRemote().sendText("UPDATE");
		}
	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
}
