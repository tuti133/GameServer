package ptit.ltm.backend.service;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.dto.request.UserLoginRequestDto;

public interface LoginService {
	ResponseDto login(UserLoginRequestDto user);
}
