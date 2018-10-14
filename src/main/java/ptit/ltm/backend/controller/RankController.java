package ptit.ltm.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ptit.ltm.backend.dto.response.RankResponseDTO;
import ptit.ltm.backend.service.RankService;

@RestController
@RequestMapping("/api")
public class RankController {

	@Autowired
	private RankService rankService;

	@GetMapping("/rank")
	public RankResponseDTO getRanks() {
		return rankService.getRanks();
	}

}
