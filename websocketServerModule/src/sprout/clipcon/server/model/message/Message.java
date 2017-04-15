package sprout.clipcon.server.model.message;

import org.json.JSONObject;

import lombok.Getter;

@Getter
public class Message {
	private String type;
	private JSONObject json;

	public Message(String type) {
		this.type = type;
		json.put("type", type);
	}

	public void add(String key, String value) {
		json.put(key, value);
	}

	public final static String REQUEST_SIGN_IN = "client reuest: sign in";
	public final static String REQUEST_SIGN_UP = "client reuest: sign up";
	public final static String REQUEST_CREATE_GROUP = "client reuest: create group";
	public final static String REQUEST_JOIN_GROUP = "client reuest: join group";
}
