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
	private Server server;	// 서버
	private Group group;	// 참여 중인 그룹
	private User user;		// user 정보
	private Session session;

	@OnOpen
	public void handleOpen(Session userSession) {
		this.session = userSession;

	}

	@OnMessage
	public void handleMessage(ChatMessage incomingChatMessage, Session userSession) throws IOException, EncodeException {
		switch ("") {
		case Message.REQUEST_SIGN_IN:
			String result = MemberAdministrator.getUserAuthentication("암호화된문자열");
			if (result == MemberAdministrator.CONFIRM) { // 서버: 승인
				server = Server.getInstance();	// 서버 객체 할당
				user = MemberAdministrator.getUserByEmail("사용자 이메일");
				server.enterUserInLobby(user);			// 서버에 사용자 입장
			} else { // 서버: 거부
			}
			break;

		case Message.REQUEST_SIGN_UP:
			break;

		case Message.REQUEST_CREATE_GROUP:
			group = server.createGroup("그룹 이름");	// 해당 이름으로 그룹 생성
			group.addUser(user, session);		// 그 그룹에 참여
			break;

		case Message.REQUEST_JOIN_GROUP:
			group = server.getGroupByPrimaryKey("그룹고유키");
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
