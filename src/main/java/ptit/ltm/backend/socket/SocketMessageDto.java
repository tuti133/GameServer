package ptit.ltm.backend.socket;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ptit.ltm.backend.entity.Question;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocketMessageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;

	private String msg;

	private String nickName;

	private int correctAnswer;
	
	private int id;
	
	private int idWin;
	
	
	private int idStay;
	
	private int matchId;
	 
    private List<Question> questionList;
}
