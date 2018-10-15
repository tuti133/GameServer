package ptit.ltm.backend.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.ltm.backend.entity.Match;
import ptit.ltm.backend.repository.MatchRepository;
import ptit.ltm.backend.service.MatchService;

@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	private MatchRepository matchRepository;
	
	@Override
	public Match create() {
		Match match = new Match();
		match.setCreatedDate(new Date());
		return matchRepository.save(match);
	}

}
