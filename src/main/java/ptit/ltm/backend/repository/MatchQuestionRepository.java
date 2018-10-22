package ptit.ltm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.ltm.backend.entity.MatchQuestion;
import ptit.ltm.backend.entity.MatchQuestionId;

@Repository
public interface MatchQuestionRepository extends JpaRepository<MatchQuestion, MatchQuestionId> {

}
