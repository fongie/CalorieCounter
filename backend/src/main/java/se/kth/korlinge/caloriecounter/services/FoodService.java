package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.repositories.FoodRepository;
import se.kth.korlinge.caloriecounter.repositories.UserRepository;

import java.util.*;

//TODO transacstional?
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
@Service
public class FoodService {
   @Autowired
   private FoodRepository foodRepository;
   @Autowired
   private UserRepository userRepository;

   Food getFood(int id) {
      Optional<Food> food = foodRepository.findById(id);
      if (!food.isPresent()) {
         throw new EntityDoesNotExistException("food", id);
      }
      return food.get();
   }
   public List<Food> getAllFoods(String username) {
      List<Food> foods = new ArrayList<>();
      User user = userRepository.findByUsername(username);
      foodRepository.findByUser(user).forEach(foods::add);
      return foods;
   }
   public Food addFood(FoodPostRequest foodPostRequest) {
      Food food = convertIntoEntity(foodPostRequest);
      return foodRepository.save(food);
   }
   public Food updateFood(int id, Map<String,Object> changes) {
      try {
         Food food = foodRepository.findById(id).get();
         changes.forEach(
               (key,value) -> {
                  validateUpdateKey(key);
                  if (key.equals("name")) {
                     food.setName((String) value);
                  }
                  if (key.equals("calories")) {
                     food.setCalories((int) value);
                  }
                  if (key.equals("protein")) {
                     food.setProtein((int) value);
                  }
         });
         return foodRepository.save(food);
      } catch (NoSuchElementException e) {
         throw new EntityDoesNotExistException("food", id);
      }
   }
   private void validateUpdateKey(String key) {
      List<String> allowedKeys = new ArrayList<>();
      allowedKeys.add("name");
      allowedKeys.add("calories");
      allowedKeys.add("protein");
      if (!allowedKeys.contains(key)) {
         throw new FieldDoesNotExistException("food", key);
      }
   }
   private Food convertIntoEntity(FoodPostRequest foodPostRequest) {
      Optional<Integer> caloriesFromPost = Optional.ofNullable(foodPostRequest.getCalories());
      Optional<Integer> proteinFromPost = Optional.ofNullable(foodPostRequest.getProtein());

      Integer calories = caloriesFromPost.isPresent() ? caloriesFromPost.get() : null;
      Integer protein = proteinFromPost.isPresent() ? proteinFromPost.get() : null;

      User user = userRepository.findByUsername(foodPostRequest.getUsername());

      Food food = new Food(
            foodPostRequest.getName(),
            calories,
            protein,
            user
            );
      return food;
   }
}