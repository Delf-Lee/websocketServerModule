package com.za.tutorial.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import lombok.Getter;
import lombok.Setter;

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
