package console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@Singleton
@ServerEndpoint("/ws/console")
@LocalBean
public class Console {
	
	static List<Session> sessions = new ArrayList<Session>();


	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {

		System.out.println(System.getProperty("jboss.node.name"));
		System.out.println("Sessia id: " + session.getId());
		System.out.println("Username: " + username);
		
		if(!sessions.contains(session)) {
					
			sessions.add(session);
			
			try {
				session.getBasicRemote().sendText("consoleId:" + session.getId());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Session with id: " + session.getId() + "closed!");
		sessions.remove(session);
		
	}
	
	
	@OnError
	public void error(Session session, Throwable t) {
		sessions.remove(session);
		t.printStackTrace();
	}
	
	@OnMessage
	public void echoTextMessage(String msg) {
	
		for(Session s : sessions) {
			try {
				s.getBasicRemote().sendText("CONSOLE&" + msg + "\n\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
