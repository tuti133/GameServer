package ptit.ltm.backend.service;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.dto.request.UserRegisterRequestDto;
import ptit.ltm.backend.entity.User;

public interface UserService {
	
	ResponseDto createUser(UserRegisterRequestDto requestDto);

	ResponseDto updateUser(User user);
	
	ResponseDto getOnlineUser(String status);
}
