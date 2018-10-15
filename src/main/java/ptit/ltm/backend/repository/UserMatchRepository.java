package ptit.ltm.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.ltm.backend.entity.UserMatches;
import ptit.ltm.backend.entity.UserMatchesId;

@Repository
public interface UserMatchRepository extends JpaRepository<UserMatches, UserMatchesId> {
	
	List<UserMatches> findByMatchId(int matchId);
}
