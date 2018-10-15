package ptit.ltm.backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.entity.UserMatches;
import ptit.ltm.backend.service.UserMatchService;
import ptit.ltm.backend.util.Constant;

@RestController
@RequestMapping("/api/")
public class UserMatchController {
	
	@Autowired
	private UserMatchService userMatchService;
	
	@PostMapping("user_match")
	public ResponseDto create(@ModelAttribute UserMatches userMatches) throws IOException {
		userMatchService.create(userMatches);
		ResponseDto response = new ResponseDto();
		response.setErrorCode(Constant.SUCCESS);
		return response;
	}
}
