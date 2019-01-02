package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.kth.korlinge.caloriecounter.data.Meal;
import se.kth.korlinge.caloriecounter.services.MealPostRequest;
import se.kth.korlinge.caloriecounter.services.MealService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Entry point for the /meals API.
 * - GET /meals
 * - POST /meals
 * - PUT /meals/:id
 */
@RestController
@RequestMapping("/meals")
public class MealController {

   @Autowired
   private MealService mealService;

   @GetMapping
   public List<Meal> getAllMeals(Principal principal) {
      return mealService.getAllMeals(principal.getName());
   }

   @PostMapping
   public Meal addMeal(@RequestBody MealPostRequest meal, Principal principal) { //autopopulates the MealPostRequest according to the json body sent
      meal.setUsername(principal.getName()); //gets currently logged in username
      return mealService.addMeal(meal);
   }

   @PutMapping("/{id}")
   public Meal updateMeal(@RequestBody Map<String,Object> changes, @PathVariable int id) {
      return mealService.updateMeal(id, changes);
   }
}