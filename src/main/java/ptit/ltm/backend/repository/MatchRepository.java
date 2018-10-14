package ptit.ltm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ptit.ltm.backend.entity.Match;


public interface MatchRepository extends JpaRepository<Match, Integer>{

}
