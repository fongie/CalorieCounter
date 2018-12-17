package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.presentation.FoodPostRequest;
import se.kth.korlinge.caloriecounter.repositories.FoodRepository;

import java.util.List;
import java.util.Map;

@Service
public class FoodService {
   @Autowired
   private FoodRepository foodRepository;

   public List<Food> getAllFoods() {
      return null;
   }
   public Food addFood(FoodPostRequest foodPostRequest) {
      return null;
   }
   public Food updateFood(int id, Map<String,Object> changes) {
      return null;
   }
}