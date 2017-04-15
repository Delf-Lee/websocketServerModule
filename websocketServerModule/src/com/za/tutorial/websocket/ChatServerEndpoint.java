package com.za.tutorial.websocket;

import java.io.IOException;
import java.util.Iterator;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chatServerEndpoint", encoders = { ChatMessageEncoder.class }, decoders = { ChatMessageDecoder.class })
public class ChatServerEndpoint {
	private Group group = new Group();

	@OnOpen
	public void handleOpen(Session userSession) {
		group.getChatroomUsers().add(userSession);
	}

	@OnMessage
	public void handleMessage(ChatMessage incomingChatMessage, Session userSession) throws IOException, EncodeException {
		String username = (String) userSession.getUserProperties().get("username");
		ChatMessage outgoingChatMessage = new ChatMessage();
		if (username == null) {
			userSession.getUserProperties().put("username", incomingChatMessage.getMessage());
			outgoingChatMessage.setName("System");
			outgoingChatMessage.setMessage("you are now connected as " + incomingChatMessage.getMessage());
			userSession.getBasicRemote().sendObject(outgoingChatMessage);
		} else {
			outgoingChatMessage.setName(username);
			outgoingChatMessage.setMessage(incomingChatMessage.getMessage());
			Iterator<Session> iterator = group.getChatroomUsers().iterator();
			while (iterator.hasNext())
				iterator.next().getBasicRemote().sendObject(outgoingChatMessage);
		}
	}

	@OnClose
	public void handleClose(Session userSession) {
		group.getChatroomUsers().remove(userSession);
	}

	@OnError
	public void handleError(Throwable t) {
	}
}
