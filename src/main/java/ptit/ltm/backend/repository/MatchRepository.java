package ptit.ltm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.ltm.backend.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer>{

}
