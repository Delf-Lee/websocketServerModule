package sprout.clipcon.server.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import lombok.Getter;
import lombok.Setter;
import sprout.clipcon.server.model.message.ChatMessage;

@Getter
@Setter
public class Group {
	public Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());

	public void send(ChatMessage message) throws IOException, EncodeException {
		Iterator<Session> iterator = chatroomUsers.iterator();
		while (iterator.hasNext())
			iterator.next().getBasicRemote().sendObject(message);
	}
}
