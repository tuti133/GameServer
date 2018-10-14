package ptit.ltm.backend.dto;

import lombok.Data;

@Data
public class RankDTO {

	private Integer pos;
	private String username;
	private String nickName;
	private Double score;
	private Double avgScore;
	private Double avgTime;
	
	public RankDTO() {
		
	}

	public RankDTO(Integer pos, String username, String nickName, Double score, Double avgScore, Double avgTime) {
		this.pos = pos;
		this.username = username;
		this.nickName = nickName;
		this.score = score;
		this.avgScore = avgScore;
		this.avgTime = avgTime;
	}

}
