package ptit.ltm.backend.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.dto.response.GetRandomQuestionResonseDto;
import ptit.ltm.backend.repository.QuestionRepository;
import ptit.ltm.backend.service.QuestionService;
import ptit.ltm.backend.util.Constant;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public ResponseDto getRandomQuestions(int number) {
		GetRandomQuestionResonseDto response = new GetRandomQuestionResonseDto();
		response.setErrorCode(Constant.SUCCESS);
		response.setQuestions(questionRepository.findRandomQuestions(number));
		return response;
	}

}
