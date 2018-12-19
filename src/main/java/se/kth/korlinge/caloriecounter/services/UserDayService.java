package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.korlinge.caloriecounter.data.UserDay;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.repositories.UserDayRepository;
import se.kth.korlinge.caloriecounter.repositories.UserRepository;

import java.util.*;

//TODO transacstional?
@Service
public class UserDayService {
   @Autowired
   private UserDayRepository userDayRepository;
   @Autowired
   private UserRepository userRepository;

   UserDay getUserDay(String username, java.sql.Date date) {
      User user = userRepository.findByUsername(username);
      Optional<UserDay> userDay = userDayRepository.findByUserAndDate(user, date);
      if (!userDay.isPresent()) {
         throw new EntityDoesNotExistException("userDay", date);
      }
      return userDay.get();
   }
   public List<UserDay> getAllUserDays(String username) {
      List<UserDay> userDays = new ArrayList<>();
      User user = userRepository.findByUsername(username);
      userDayRepository.findByUser(user).forEach(userDays::add);
      return userDays;
   }
   //check that date doesnt already exist for this user
   private void validateUserDay(User user, java.sql.Date date) {
      Optional<UserDay> userDay = userDayRepository.findByUserAndDate(user, date);
      if (userDay.isPresent()) {
         throw new AlreadyExistsException("userDay", "date", date);
      }
   }
   public UserDay addUserDay(UserDayPostRequest userDayPostRequest) {
      UserDay userDay = convertIntoEntity(userDayPostRequest);
      return userDayRepository.save(userDay);
   }
   public UserDay updateUserDay(int id, Map<String,Object> changes) {
      try {
         UserDay userDay = userDayRepository.findById(id).get();
         changes.forEach(
               (key,value) -> {
                  validateUpdateKey(key);
                  if (key.equals("weight")) {
                     userDay.setWeight((double) value);
                  }
                  if (key.equals("date")) {
                     userDay.setDate((java.sql.Date) value);
                  }
         });
         return userDayRepository.save(userDay);
      } catch (NoSuchElementException e) {
         throw new EntityDoesNotExistException("userDay", id);
      }
   }
   private void validateUpdateKey(String key) {
      List<String> allowedKeys = new ArrayList<>();
      allowedKeys.add("weight");
      allowedKeys.add("date");
      if (!allowedKeys.contains(key)) {
         throw new FieldDoesNotExistException("userDay", key);
      }
   }
   private UserDay convertIntoEntity(UserDayPostRequest userDayPostRequest) {
      Optional<java.sql.Date> dateFromPost = Optional.ofNullable(userDayPostRequest.getDate());

      java.sql.Date date = dateFromPost.isPresent() ? dateFromPost.get() : getCurrentDate();

      User user = userRepository.findByUsername(userDayPostRequest.getUsername());

      validateUserDay(user, date);

      UserDay userDay = new UserDay(
            user,
            userDayPostRequest.getWeight(),
            date
            );
      return userDay;
   }
   private java.sql.Date getCurrentDate() {
      TimeZone tz = TimeZone.getTimeZone("Europe/Stockholm"); //note have to change for international customers
      Calendar cal = Calendar.getInstance(tz);
      Date now = cal.getTime();
      java.sql.Date currentDate = new java.sql.Date(now.getTime());
      return currentDate;
   }
}