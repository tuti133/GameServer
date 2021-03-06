package ptit.ltm.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ptit.ltm.backend.dto.request.UserLoginRequestDto;
import ptit.ltm.backend.dto.response.UserLoginResponseDto;
import ptit.ltm.backend.entity.User;
import ptit.ltm.backend.repository.UserRepository;
import ptit.ltm.backend.service.LoginService;
import ptit.ltm.backend.util.Constant;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserLoginResponseDto login(UserLoginRequestDto userLogin) {
		UserLoginResponseDto response = new UserLoginResponseDto();
		User user = userRepository.findByUsername(userLogin.getUsername()).orElse(null);
		if (user == null) {
			response.setErrorCode(Constant.ERROR);
			response.setMsg("Username does not exist!");
			return response;
		}
		if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
			response.setErrorCode(Constant.ERROR);
			response.setMsg("Wrong password!");
			return response;
		}
		System.err.println(user.toString());
		if (user.getStatus().equals(Constant.AVAILABLE_STATUS)) {
			response.setErrorCode(Constant.ERROR);
			response.setMsg("Your account has been logged in!");
			return response;
		}
		user.setStatus(Constant.AVAILABLE_STATUS);
		userRepository.save(user);
		response.setErrorCode(Constant.SUCCESS);
		response.setUser(user);
		return response;
	}

}
