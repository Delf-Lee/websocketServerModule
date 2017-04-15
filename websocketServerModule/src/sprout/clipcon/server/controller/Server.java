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
	private Map<String, Group> groups = Collections.synchronizedMap(new HashMap<String, Group>());	// ���� �� �����Ѵ� �׷�
	private Set<User> userOnLobby = Collections.synchronizedSet(new HashSet<User>());				// �׷쿡 �������� ���� ���� ���� �����

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
			userOnLobby.remove(user);		// ��� �� ����ڸ�
			groups.get(key).addUser(user);	// �׷����� �̵�
			return true;
		}
		return false;
	}
}
