package ptit.ltm.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@IdClass(MatchQuestionId.class)
@Table(name = "match_question")
@Data
public class MatchQuestion {
	
	@Id
	@Column(name = "match_id")
	private int matchId;
	
	@Id
	@Column(name = "question_id")
	private int questionId;
}
