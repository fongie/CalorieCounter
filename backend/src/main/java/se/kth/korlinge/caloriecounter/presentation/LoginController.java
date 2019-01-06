package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.kth.korlinge.caloriecounter.services.LoginService;

import java.security.Principal;

/**
 * Entry point for authentication API
 * - POST /login (handled by Spring Security entirely)
 * - GET /loggedin
 */
@CrossOrigin
@RestController
public class LoginController {

   @Autowired
   private LoginService loginService;

   /**
    * Return the currently logged in username.
    * @param principal
    * @return
    */
   @GetMapping("/loggedin")
   public String loggedin(Principal principal) {
      return principal.getName();
   }

   /**
    * Browse to the /registration page to register new users. This could be turned into a REST API aswell but since I intend to use this app myself and host it online but not allow the public to register,
    * I will keep it this way and turn off this method after deploying and registering my user.
    * In short: not for use in production
    * @return
    */
   @GetMapping("/registration")
   public ModelAndView registration() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("registration");
      return modelAndView;
   }

   /**
    * Register a new user. See doc for GET /registration.
    * @param username
    * @param password
    * @return
    */
   @PostMapping("/registration")
   public ModelAndView register(String username, String password) {
      ModelAndView modelAndView = new ModelAndView();
      boolean registered = loginService.register(username, password);
      if (!registered)
         return new ModelAndView("redirect:/registration?error");
      return new ModelAndView("redirect:/registration?success");
   }
}
