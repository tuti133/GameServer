package ptit.ltm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.dto.request.UserLoginRequestDto;
import ptit.ltm.backend.dto.request.UserRegisterRequestDto;
import ptit.ltm.backend.service.LoginService;
import ptit.ltm.backend.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseDto login(@ModelAttribute UserLoginRequestDto requestDto) {
		return loginService.login(requestDto);
	}
	
	@PostMapping("/register")
	public ResponseDto register(@ModelAttribute UserRegisterRequestDto requestDto ) {
		return userService.createUser(requestDto);
	}

}
