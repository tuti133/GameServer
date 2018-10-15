package ptit.ltm.backend.service;

import ptit.ltm.backend.dto.ResponseDto;

public interface QuestionService {
	ResponseDto getRandomQuestions(int number);
}
