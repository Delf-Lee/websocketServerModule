package sprout.clipcon.server.model.message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
	private JSONObject tmp;

	public void destroy() {
	}

	public void init(EndpointConfig arg0) {
		tmp = new JSONObject();
	}

	public String encode(ChatMessage message) throws EncodeException {

		tmp.put("name", message.getName());
		tmp.put("message", message.getMessage());
		return tmp.toString();
		// return Json.createObjectBuilder().add("name", message.getName()).add("message", message.getMessage()).build().toString();
	}
}
