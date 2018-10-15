package ptit.ltm.backend.service;

import java.io.IOException;

import ptit.ltm.backend.entity.UserMatches;

public interface UserMatchService {
	void create(UserMatches userMatches) throws IOException;
}
