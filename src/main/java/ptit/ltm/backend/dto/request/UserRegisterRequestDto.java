package ptit.ltm.backend.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
	
	private String username;

	private String password;

	private MultipartFile avatar;
	
	private String ingameName;
}
