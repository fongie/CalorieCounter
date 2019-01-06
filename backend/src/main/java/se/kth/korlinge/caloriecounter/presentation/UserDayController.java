package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.kth.korlinge.caloriecounter.data.UserDay;
import se.kth.korlinge.caloriecounter.services.UserDayPostRequest;
import se.kth.korlinge.caloriecounter.services.UserDayService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Entry point for the /userdays API. Contains date and weight statistics for users.
 * - GET /userdays
 * - POST /userdays
 * - PUT /userdays/:id
 */
@CrossOrigin
@RestController
@RequestMapping("/userdays")
public class UserDayController {

   @Autowired
   private UserDayService userDayService;

   /**
    * Get all user days for the logged in user.
    * @param principal
    * @return
    */
   @GetMapping
   public List<UserDay> getAllUserDays(Principal principal) {
      return userDayService.getAllUserDays(principal.getName());
   }

   /**
    * Add a new user day.
    * @param userDay
    * @param principal
    * @return
    */
   @PostMapping
   public UserDay addUserDay(@RequestBody UserDayPostRequest userDay, Principal principal) { //autopopulates the UserDayPostRequest according to the json body sent
      userDay.setUsername(principal.getName()); //gets currently logged in username
      return userDayService.addUserDay(userDay);
   }

   /**
    * Change a user day.
    * @param changes
    * @param id
    * @return
    */
   @PutMapping("/{id}")
   public UserDay updateUserDay(@RequestBody Map<String,Object> changes, @PathVariable int id) {
      return userDayService.updateUserDay(id, changes);
   }
}
