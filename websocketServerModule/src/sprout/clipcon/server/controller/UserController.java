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
import sprout.clipcon.server.model.message.Message;

@ServerEndpoint(value = "/chatServerEndpoint", encoders = { ChatMessageEncoder.class }, decoders = { ChatMessageDecoder.class })
public class UserController {
	private Server server;	// ����
	private Group group;	// ���� ���� �׷�
	private User user;		// user ����
	private Session session;

	@OnOpen
	public void handleOpen(Session userSession) {
		this.session = userSession;

	}

	@OnMessage
	public void handleMessage(ChatMessage incomingChatMessage, Session userSession) throws IOException, EncodeException {
		switch ("") {
		case Message.REQUEST_SIGN_IN:
			String result = MemberAdministrator.getUserAuthentication("��ȣȭ�ȹ��ڿ�");
			if (result == MemberAdministrator.CONFIRM) { // ����: ����
				server = Server.getInstance();	// ���� ��ü �Ҵ�
				user = MemberAdministrator.getUserByEmail("����� �̸���");
				server.enterUserInLobby(user);			// ������ ����� ����
			} else { // ����: �ź�
			}
			break;

		case Message.REQUEST_SIGN_UP:
			break;

		case Message.REQUEST_CREATE_GROUP:
			group = server.createGroup("�׷� �̸�");	// �ش� �̸����� �׷� ����
			group.addUser(user, session);		// �� �׷쿡 ����
			break;

		case Message.REQUEST_JOIN_GROUP:
			group = server.getGroupByPrimaryKey("�׷����Ű");
			group.addUser(user, session);
			break;

		default:
			break;
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
