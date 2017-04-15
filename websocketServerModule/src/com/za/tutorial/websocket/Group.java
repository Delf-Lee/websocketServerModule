package com.za.tutorial.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Group {
	public Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());
}
