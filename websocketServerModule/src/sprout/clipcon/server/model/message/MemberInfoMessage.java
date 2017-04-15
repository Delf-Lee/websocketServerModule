package sprout.clipcon.server.model.message;

import lombok.Getter;

@Getter
public class MemberInfoMessage extends Message {
	private String email;
	private String password;
	private String name;
}
