package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.services.FoodPostRequest;
import se.kth.korlinge.caloriecounter.services.FoodService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Entry point for the /foods API.
 * - GET /foods
 * - POST /foods
 * - PUT /foods/:id
 */
@CrossOrigin(allowCredentials = "true")
@RestController
@RequestMapping("/foods")
public class FoodController {

   @Autowired
   private FoodService foodService;

   /**
    * Get all food entities for logged in user.
    * @param principal
    * @return
    */
   @GetMapping
   public List<Food> getAllFoods(Principal principal) {
      return foodService.getAllFoods(principal.getName());
   }

   /**
    * Add a new type of food (dish).
    * @param food
    * @param principal
    * @return
    */
   @PostMapping
   public Food addFood(@RequestBody FoodPostRequest food, Principal principal) { //autopopulates the FoodPostRequest according to the json body sent
      food.setUsername(principal.getName()); //gets currently logged in username
      return foodService.addFood(food);
   }

   /**
    * Change some value(s) in a food (dish). Will take any number of valid fields to change.
    * @param changes
    * @param id
    * @return
    */
   @PutMapping("/{id}")
   public Food updateFood(@RequestBody Map<String,Object> changes, @PathVariable int id) {
      return foodService.updateFood(id, changes);
   }
}
