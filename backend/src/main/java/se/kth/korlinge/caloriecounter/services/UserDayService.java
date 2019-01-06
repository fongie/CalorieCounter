package se.kth.korlinge.caloriecounter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.korlinge.caloriecounter.data.UserDay;
import se.kth.korlinge.caloriecounter.data.User;
import se.kth.korlinge.caloriecounter.repositories.UserDayRepository;
import se.kth.korlinge.caloriecounter.repositories.UserRepository;

import java.util.*;

/**
 * Service that handles logic concerning the /userdays API and userday entities.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
@Service
public class UserDayService {
   @Autowired
   private UserDayRepository userDayRepository;
   @Autowired
   private UserRepository userRepository;

   /**
    * Get all user days for a certain user.
    * @param username
    * @return
    */
   public List<UserDay> getAllUserDays(String username) {
      List<UserDay> userDays = new ArrayList<>();
      User user = userRepository.findByUsername(username);
      userDayRepository.findByUser(user).forEach(userDays::add);
      return userDays;
   }

   /**
    * Add a new user day.
    * @param userDayPostRequest
    * @return
    * @throws AlreadyExistsException If the specified date already exists for this user.
    */
   public UserDay addUserDay(UserDayPostRequest userDayPostRequest) throws AlreadyExistsException {
      UserDay userDay = convertIntoEntity(userDayPostRequest);
      return userDayRepository.save(userDay);
   }

   /**
    * Change one or more attributes for a user day.
    * @param id
    * @param changes
    * @return
    * @throws FieldDoesNotExistException If trying to change a field that does not exist or is forbidden.
    * @throws EntityDoesNotExistException If the entity does not exist.
    */
   public UserDay updateUserDay(int id, Map<String,Object> changes) throws FieldDoesNotExistException, EntityDoesNotExistException {
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
   /**
    * Get a userday by its username and a date.
    * @param username
    * @param date
    * @return
    */
   UserDay getUserDay(String username, java.sql.Date date) throws EntityDoesNotExistException {
      User user = userRepository.findByUsername(username);
      Optional<UserDay> userDay = userDayRepository.findByUserAndDate(user, date);
      if (!userDay.isPresent()) {
         throw new EntityDoesNotExistException("userDay", date);
      }
      return userDay.get();
   }
   private void validateUpdateKey(String key) throws FieldDoesNotExistException {
      List<String> allowedKeys = new ArrayList<>();
      allowedKeys.add("weight");
      allowedKeys.add("date");
      if (!allowedKeys.contains(key)) {
         throw new FieldDoesNotExistException("userDay", key);
      }
   }
   private UserDay convertIntoEntity(UserDayPostRequest userDayPostRequest) throws AlreadyExistsException {
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
   //check that date doesnt already exist for this user
   private void validateUserDay(User user, java.sql.Date date) throws AlreadyExistsException {
      Optional<UserDay> userDay = userDayRepository.findByUserAndDate(user, date);
      if (userDay.isPresent()) {
         throw new AlreadyExistsException("userDay", "date", date);
      }
   }
   private java.sql.Date getCurrentDate() {
      TimeZone tz = TimeZone.getTimeZone("Europe/Stockholm"); //note have to change for international customers
      Calendar cal = Calendar.getInstance(tz);
      Date now = cal.getTime();
      java.sql.Date currentDate = new java.sql.Date(now.getTime());
      return currentDate;
   }
}