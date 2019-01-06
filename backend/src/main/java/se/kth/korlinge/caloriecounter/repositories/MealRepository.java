package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.Meal;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.data.UserDay;

import java.util.List;

/**
 * Repository that handles database calls concerning the meal table.
 */
@Repository
public interface MealRepository extends CrudRepository<Meal,Integer> {
   /**
    * Find all meals for a certain user day.
    * @param userDay
    * @return
    */
   List<Meal> findByUserDay(UserDay userDay);
}