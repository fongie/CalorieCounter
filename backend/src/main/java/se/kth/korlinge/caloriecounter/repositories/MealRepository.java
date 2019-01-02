package se.kth.korlinge.caloriecounter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.kth.korlinge.caloriecounter.data.Meal;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.data.UserDay;

import java.util.List;

@Repository
public interface MealRepository extends CrudRepository<Meal,Integer> {
   List<Meal> findByUserDay(UserDay userDay);
}