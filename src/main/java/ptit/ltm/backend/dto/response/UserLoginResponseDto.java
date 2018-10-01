package ptit.ltm.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponseDto extends ResponseDto {
	private User user;
}
