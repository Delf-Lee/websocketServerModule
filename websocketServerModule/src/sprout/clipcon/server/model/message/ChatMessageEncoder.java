package sprout.clipcon.server.model.message;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
	public void destroy() {}
	public void init(EndpointConfig arg0) {}
	public String encode(ChatMessage message) throws EncodeException {
		return Json.createObjectBuilder().add("name", message.getName())
                                         .add("message", message.getMessage())
                                         .build().toString();
	}
}
