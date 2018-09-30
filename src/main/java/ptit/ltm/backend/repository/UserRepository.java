package ptit.ltm.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ptit.ltm.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
