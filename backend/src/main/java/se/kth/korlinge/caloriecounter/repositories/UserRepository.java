package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.User;

/**
 * Repository that handles database calls concerning the user table.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
