package sprout.clipcon.server.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sprout.clipcon.server.model.Group;
import sprout.clipcon.server.model.User;

public class Server {
	private static Server uniqueInstance;
	private Map<String, Group> groups = Collections.synchronizedMap(new HashMap<String, Group>());	// 서버 내 존재한는 그룹
	private Set<User> userOnLobby = Collections.synchronizedSet(new HashSet<User>());				// 그룹에 참여하지 않은 접속 중인 사용자

	private Server() {
	}

	public static Server getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Server();
		}
		return uniqueInstance;
	}

	public void enterUser(User user) {
		userOnLobby.add(user);
	}

	public boolean joinGroup(String key, User user) {
		Group targetGroup = groups.get(key);
		if (targetGroup != null) {
			userOnLobby.remove(user);		// 대기 중 사용자를
			groups.get(key).addUser(user);	// 그룹으로 이동
			return true;
		}
		return false;
	}
}
