package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.data.User;

import java.util.List;

/**
 * Repository that handles database calls concerning the food table.
 */
@Repository
public interface FoodRepository extends CrudRepository<Food,Integer> {
   /**
    * Find all food entities created by a certain user
    * @param user
    * @return
    */
   List<Food> findByUser(User user);
}
