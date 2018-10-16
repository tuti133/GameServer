package ptit.ltm.backend.dto;

import lombok.Data;

@Data
public class RankDTO {

	private String username;
	private String nickName;
	private Double score;
	private Double avgScore;
	private Double avgTime;

	public RankDTO() {

	}

	public RankDTO(String username, String nickName, Double score, Double avgScore, Double avgTime) {
		this.username = username;
		this.nickName = nickName;
		this.score = score;
		this.avgScore = avgScore;
		this.avgTime = avgTime;
	}

}
