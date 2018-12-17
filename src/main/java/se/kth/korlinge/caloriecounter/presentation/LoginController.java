package se.kth.korlinge.caloriecounter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.kth.korlinge.caloriecounter.services.LoginService;


@RestController
public class LoginController {

   @Autowired
   private LoginService loginService;

   @GetMapping("/hello")
   public String hello() {
      return "hello";
   }

   @GetMapping("/webhome")
   public ModelAndView getWebHome() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("home");
      return modelAndView;
   }

   @GetMapping({"/", "/login"})
   public ModelAndView getStartPage() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("login");
      return modelAndView;
   }

   @GetMapping("/registration")
   public ModelAndView registration() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("registration");
      return modelAndView;
   }

   @PostMapping("/registration")
   public ModelAndView register(String username, String password) {
      ModelAndView modelAndView = new ModelAndView();
      boolean registered = loginService.register(username, password);
      if (!registered)
         return new ModelAndView("redirect:/registration?error");
      return new ModelAndView("redirect:/registration?success");
   }
}
