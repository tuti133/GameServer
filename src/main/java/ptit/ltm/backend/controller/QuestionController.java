package ptit.ltm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/questions/random={number}")
	public ResponseDto getRandomQuestion(@PathVariable int number) {
		return questionService.getRandomQuestions(number);
	}
}
