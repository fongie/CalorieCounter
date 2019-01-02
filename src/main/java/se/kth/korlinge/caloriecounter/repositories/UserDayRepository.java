package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.data.UserDay;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDayRepository extends CrudRepository<UserDay,Integer> {
   List<UserDay> findByUser(User user);
   Optional<UserDay> findByUserAndDate(User user, Date date);
}
