package ptit.ltm.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ptit.ltm.backend.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query(value = "SELECT * FROM questions order by rand() limit ?1", nativeQuery = true)
	List<Question> findRandomQuestions(int number);

}
