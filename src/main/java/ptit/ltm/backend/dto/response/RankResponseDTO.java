package ptit.ltm.backend.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ptit.ltm.backend.dto.RankDto;
import ptit.ltm.backend.dto.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
public class RankResponseDTO extends ResponseDto {

	private List<RankDto> rankList;

}
