package ptit.ltm.backend.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
	
	private String username;

	private String password;
	
	private String ingameName;
}
