package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.services.FoodService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/foods")
public class FoodController {

   @Autowired
   private FoodService foodService;

   @GetMapping
   public List<Food> getAllFoods(Principal principal) {
      return foodService.getAllFoods(principal.getName());
   }

   @PostMapping
   public Food addFood(@RequestBody FoodPostRequest food, Principal principal) { //autopopulates the FoodPostRequest according to the json body sent
      food.setUsername(principal.getName()); //gets currently logged in username
      return foodService.addFood(food);
   }

   @PutMapping("/{id}")
   public Food updateFood(@RequestBody Map<String,Object> changes, @PathVariable int id) {
      return foodService.updateFood(id, changes);
   }
}
