package ptit.ltm.backend.service;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.dto.request.UserRegisterRequestDto;
import ptit.ltm.backend.entity.User;

public interface UserService {
	
	public ResponseDto createUser(UserRegisterRequestDto requestDto);

	public ResponseDto updateUser(User user);
	
}
