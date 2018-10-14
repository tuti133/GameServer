package ptit.ltm.backend.socket;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;

	private String msg;

	private String nickName;

	private int id;
}
