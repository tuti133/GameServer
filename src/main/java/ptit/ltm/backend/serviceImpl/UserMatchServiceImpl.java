package ptit.ltm.backend.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.ltm.backend.entity.User;
import ptit.ltm.backend.entity.UserMatches;
import ptit.ltm.backend.repository.UserMatchRepository;
import ptit.ltm.backend.repository.UserRepository;
import ptit.ltm.backend.service.UserMatchService;
import ptit.ltm.backend.socket.SocketController;
import ptit.ltm.backend.util.Constant;

@Service
public class UserMatchServiceImpl implements UserMatchService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMatchRepository userMatchRepository;

	@Override
	public void create(UserMatches userMatches) throws IOException {
		userMatchRepository.save(userMatches);
		List<UserMatches> list = userMatchRepository.findByMatchId(userMatches.getMatchId());
		UserMatches player1 = list.get(0);
		UserMatches player2 = list.get(1);
		if (player1.getTime() != 0 && player2.getTime() != 0) {			
			if (player1.getCorrectAnswers() == Constant.RANDOM_QUESTION_NUMBER
					&& player1.getCorrectAnswers() == player2.getCorrectAnswers()) {
				if (player1.getTime() < player2.getTime()) {
					player1.setPoint(1.0);
					player1.setResult(Constant.WIN);
					player2.setPoint(0.0);
					player2.setResult(Constant.LOST);

				} else if (player1.getTime() > player2.getTime()) {
					player2.setPoint(1.0);
					player2.setResult(Constant.WIN);
					player1.setPoint(0.0);
					player1.setResult(Constant.LOST);

				} else {
					player1.setPoint(0.5);
					player1.setResult(Constant.DRAW);
					player2.setPoint(0.5);
					player2.setResult(Constant.DRAW);
				}

			} else if (player1.getCorrectAnswers() == Constant.RANDOM_QUESTION_NUMBER) {
				player1.setPoint(1.0);
				player1.setResult(Constant.WIN);
				player2.setPoint(0.0);
				player2.setResult(Constant.LOST);
			} else if (player2.getCorrectAnswers() == Constant.RANDOM_QUESTION_NUMBER) {
				player2.setPoint(1.0);
				player2.setResult(Constant.WIN);
				player1.setPoint(0.0);
				player1.setResult(Constant.LOST);
			} else {
				player1.setPoint(0.5);
				player1.setResult(Constant.DRAW);
				player2.setPoint(0.5);
				player2.setResult(Constant.DRAW);
			}
			userMatchRepository.save(player1);
			userMatchRepository.save(player2);
		
			User user1 = userRepository.findById(player1.getUserId()).get();
			user1.setScore(user1.getScore()+player1.getPoint());
			User user2 = userRepository.findById(player2.getUserId()).get();
			user2.setScore(user2.getScore()+player2.getPoint());
			user1.setStatus(Constant.AVAILABLE_STATUS);
			user2.setStatus(Constant.AVAILABLE_STATUS);
			userRepository.save(user1);
			userRepository.save(user2);
			SocketController.sendResult(player1);
			SocketController.sendResult(player2);
		}
	}

}
