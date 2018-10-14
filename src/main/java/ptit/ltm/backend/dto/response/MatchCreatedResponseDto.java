package ptit.ltm.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.entity.Match;

@Getter
@Setter
@NoArgsConstructor
public class MatchCreatedResponseDto extends ResponseDto {
	private Match match;
}