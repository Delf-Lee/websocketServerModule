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
	private MemberAdministrator MemberAdministrator = new MemberAdministrator();
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

	/**
	 * ����ڰ� ������ ���� 
	 * @param user ������ ������ ����� */
	public void enterUserInLobby(User user) {
		userOnLobby.add(user);
	}

	public void exitUSerAtLobby(User user) {
		// TODO: static?
		userOnLobby.remove(user);
	}

	// public void

	/**
	 * �ش� �׷쿡 ����� �߰�
	 * @param key �׷� ���� Ű
	 * @param user �׷쿡 ���� �� ����� 
	 * @return �׷��� ���� ����. �׷��� �������� ������ false, �����ϰ� ����ڰ� ���������� �׷쿡 �߰� ������ true. */
	public Group getGroupByPrimaryKey(String key) {
		Group targetGroup = groups.get(key);
		if (targetGroup != null) {
		}
		return targetGroup;
	}

	public Group createGroup(String name) {
		String groupKey = generatePrimaryKey();
		return groups.put(groupKey, new Group(groupKey, name));
	}

	private String generatePrimaryKey() {
		return "";
	}
}
