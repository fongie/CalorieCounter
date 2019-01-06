package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.data.UserDay;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository that handles database calls concerning the user_day table.
 */
@Repository
public interface UserDayRepository extends CrudRepository<UserDay,Integer> {
   /**
    * Find userdays by user.
    * @param user
    * @return
    */
   List<UserDay> findByUser(User user);

   /**
    * Find a userday by its user and date.
    * @param user
    * @param date
    * @return
    */
   Optional<UserDay> findByUserAndDate(User user, Date date);
}
