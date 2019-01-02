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
 * Entry point for the /userdays API.
 * - GET /userdays
 * - POST /userdays
 * - PUT /userdays/:id
 */
@RestController
@RequestMapping("/userdays")
public class UserDayController {

   @Autowired
   private UserDayService userDayService;

   @GetMapping
   public List<UserDay> getAllUserDays(Principal principal) {
      return userDayService.getAllUserDays(principal.getName());
   }

   @PostMapping
   public UserDay addFood(@RequestBody UserDayPostRequest userDay, Principal principal) { //autopopulates the FoodPostRequest according to the json body sent
      userDay.setUsername(principal.getName()); //gets currently logged in username
      return userDayService.addUserDay(userDay);
   }

   @PutMapping("/{id}")
   public UserDay updateFood(@RequestBody Map<String,Object> changes, @PathVariable int id) {
      return userDayService.updateUserDay(id, changes);
   }
}
