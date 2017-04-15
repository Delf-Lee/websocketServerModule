package com.za.tutorial.websocket;
import java.io.StringReader;
import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
	public void destroy() {}
	public void init(EndpointConfig arg0) {}
	public ChatMessage decode(String message) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessage((Json.createReader(new StringReader(message)).readObject()).getString("message"));
		return chatMessage;
	}
	public boolean willDecode(String message) {
		boolean flag = true;
		try {Json.createReader(new StringReader(message)).readObject();} 
		catch (Exception e) {flag = false;}
		return flag;
	}
}
