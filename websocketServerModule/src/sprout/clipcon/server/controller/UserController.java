package sprout.clipcon.server.controller;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import sprout.clipcon.server.model.Group;
import sprout.clipcon.server.model.User;
import sprout.clipcon.server.model.message.ChatMessage;
import sprout.clipcon.server.model.message.ChatMessageDecoder;
import sprout.clipcon.server.model.message.ChatMessageEncoder;

@ServerEndpoint(value = "/chatServerEndpoint", encoders = { ChatMessageEncoder.class }, decoders = { ChatMessageDecoder.class })
public class UserController {
	private Server server = Server.getInstance();	// 서버
	private Group group = new Group();				// 참여 중인 그룹
	private User user = new User();					// user 정보

	@OnOpen
	public void handleOpen(Session userSession) {
		server.enterUser(user);
		group.getUsers().add(userSession);
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
			group.send(outgoingChatMessage);
		}
	}

	@OnClose
	public void handleClose(Session userSession) {
		group.getUsers().remove(userSession);
	}

	@OnError
	public void handleError(Throwable t) {
	}
}
