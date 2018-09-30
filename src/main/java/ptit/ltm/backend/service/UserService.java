package ptit.ltm.backend.service;

import ptit.ltm.backend.dto.PostResponseDto;
import ptit.ltm.backend.entity.User;

public interface UserService {
	
	public PostResponseDto createUser(User user);

	public PostResponseDto updateUser(User user);
	
}
