package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.data.User;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food,Integer> {
   List<Food> findByUser(User user);
}
