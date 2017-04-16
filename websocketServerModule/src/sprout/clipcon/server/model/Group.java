package sprout.clipcon.server.model;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import lombok.Getter;
import lombok.Setter;
import sprout.clipcon.server.controller.Server;
import sprout.clipcon.server.model.message.Message;

@Getter
@Setter
public class Group {
	private String primaryKey;
	private String name;
	private Server server = Server.getInstance();
	public Map<User, Session> users = Collections.synchronizedMap(new HashMap<User, Session>());

	public Group(String primaryKey, String name) {
		this.primaryKey = primaryKey;
		this.name = name;
	}

	public void send(Message message) throws IOException, EncodeException {

		for (User key : users.keySet()) {
			System.out.println(key);
			users.get(key).getBasicRemote().sendObject(message);
		}
		// Iterator<User, Session> iterator = users.iterator();
		// while (iterator.hasNext()) {
		// iterator.next().getBasicRemote().sendObject(message);
		// }
	}

	public void tmpSendMethod(Session session, Message message) throws IOException, EncodeException {
		session.getBasicRemote().sendObject(message);
	}

	public boolean addUser(User user, Session session) {
		users.put(user, session);
		return true;
	}
}
