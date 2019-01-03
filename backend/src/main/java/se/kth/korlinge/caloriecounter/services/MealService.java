package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.korlinge.caloriecounter.data.Food;
import se.kth.korlinge.caloriecounter.data.Meal;
import se.kth.korlinge.caloriecounter.data.UserDay;
import se.kth.korlinge.caloriecounter.repositories.MealRepository;

import java.util.*;

@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
@Service
public class MealService {
   @Autowired
   private MealRepository mealRepository;
   @Autowired
   private FoodService foodService;
   @Autowired
   private UserDayService userDayService;

   public List<Meal> getAllMeals(String username) {
      List<UserDay> userDays = userDayService.getAllUserDays(username);

      List<Meal> meals = new ArrayList<>();
      for (UserDay userDay : userDays) {
         mealRepository.findByUserDay(userDay).forEach(meals::add);
      }
      return meals;
   }
   public List<Meal> getAllMealsForDay(String username, java.sql.Date date) {
      UserDay userDay = userDayService.getUserDay(username,date);
      List<Meal> meals = new ArrayList<>();
      mealRepository.findByUserDay(userDay).forEach(meals::add);
      return meals;
   }

   public void deleteMeal(int id, String username) throws AccessViolationException {
      try {
         Meal mealToDelete = mealRepository.findById(id).get();
         checkForAccessViolation(mealToDelete, username);
         mealRepository.delete(mealToDelete);
      } catch (NoSuchElementException e) {
         throw new EntityDoesNotExistException("meal", id);
      }
   }
   public Meal addMeal(MealPostRequest mealPostRequest) {
      Meal meal = convertIntoEntity(mealPostRequest);
      return mealRepository.save(meal);
   }
   public Meal updateMeal(int id, Map<String,Object> changes, String username) throws EntityDoesNotExistException, AccessViolationException {
      try {
         Meal meal = mealRepository.findById(id).get();
         checkForAccessViolation(meal, username);
         changes.forEach(
               (key,value) -> {
                  validateUpdateKey(key);
                  if (key.equals("food")) {
                     Food food = foodService.getFood((int) value);
                     meal.setFood(food);
                  }
                  if (key.equals("date")) {
                     UserDay userDay = userDayService.getUserDay(
                           SecurityContextHolder .getContext() .getAuthentication() .getName(),
                           (java.sql.Date) value
                     );
                     meal.setUserDay(userDay);
                  }
         });
         return mealRepository.save(meal);
      } catch (NoSuchElementException e) {
         throw new EntityDoesNotExistException("meal", id);
      }
   }
   private void checkForAccessViolation(Meal meal, String username) throws AccessViolationException {
      if (!meal.getUserDay().getUser().getUsername().equals(username)) {
         throw new AccessViolationException();
      }
   }

   private void validateUpdateKey(String key) {
      List<String> allowedKeys = new ArrayList<>();
      allowedKeys.add("food");
      allowedKeys.add("date");
      if (!allowedKeys.contains(key)) {
         throw new FieldDoesNotExistException("meal", key);
      }
   }
   private Meal convertIntoEntity(MealPostRequest mealPostRequest) {
      Food food = foodService.getFood(mealPostRequest.getFood());

      Optional<Date> dateFromPost = Optional.ofNullable(mealPostRequest.getDate());

      UserDay userDay;
      if (dateFromPost.isPresent()) {
         userDay = userDayService.getUserDay(mealPostRequest.getUsername(), mealPostRequest.getDate());
      } else {
         java.sql.Date today = getCurrentDate();
         userDay = userDayService.getUserDay(mealPostRequest.getUsername(), today);
      }

      Meal meal = new Meal(
            food,
            userDay
            );
      return meal;
   }

   private java.sql.Date getCurrentDate() {
      TimeZone tz = TimeZone.getTimeZone("Europe/Stockholm"); //note have to change for international customers
      Calendar cal = Calendar.getInstance(tz);
      Date now = cal.getTime();
      java.sql.Date currentDate = new java.sql.Date(now.getTime());
      return currentDate;
   }
}