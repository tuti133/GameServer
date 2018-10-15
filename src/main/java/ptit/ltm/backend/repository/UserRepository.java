package ptit.ltm.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ptit.ltm.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u "
			+ "WHERE (:status = '0' AND (u.status = '2' OR u.status = '3')) "
			+ "OR status = :status")
	List<User> findOnlineUser(@Param("status") String status);
	
}
