package ptit.ltm.backend.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Entity
@IdClass(UserMatchesId.class)
@Table(name = "user_matches")
@Data
public class UserMatches implements Serializable {

	private static final long serialVersionUID = -6308732823772891320L;

	@Id
	@Column(name = "match_id")
	private int matchId;

	@Id
	@Column(name = "user_id")
	private int userId;

	@Column(name = "time")
	private int time;

	@Column(name = "correct_answers")
	private int correctAnswers;

	@Column(name = "result")
	private int result;

	@Column(name = "point")
	private Double point;
}
