package se.kth.korlinge.caloriecounter.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a row in the food table in the database.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Food {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @NotNull
   private String name;

   private Integer calories;
   private Integer protein;

   @JsonIgnore //when JSON is returned, don't include the user in the food items. It is redundant
   @NotNull
   @ManyToOne
   private User user;

   public Food() {

   }

   public Food(String name, Integer calories, Integer protein, User user) {
      this.name = name;
      this.calories = calories;
      this.protein = protein;
      this.user = user;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Integer getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getCalories() {
      return calories;
   }

   public void setCalories(Integer calories) {
      this.calories = calories;
   }

   public Integer getProtein() {
      return protein;
   }

   public void setProtein(Integer protein) {
      this.protein = protein;
   }
}
