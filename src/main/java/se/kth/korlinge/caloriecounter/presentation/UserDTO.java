package se.kth.korlinge.caloriecounter.presentation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {
   @NotNull
   @NotEmpty
   private String username;

   @NotNull
   @NotEmpty
   private String password;

   public UserDTO() {

   }
   public UserDTO(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password) {
      this.username = username;
      this.password = password;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
