package se.kth.korlinge.caloriecounter.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a row in the meal table in the database.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meal {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @NotNull
   @ManyToOne
   private Food food;

   @NotNull
   @ManyToOne
   private UserDay userDay;

   public Meal() {

   }

   public Meal(@NotNull Food food, @NotNull UserDay userDay) {
      this.food = food;
      this.userDay = userDay;
   }

   public int getId() {
      return id;
   }
   public Food getFood() {
      return food;
   }

   public void setFood(Food food) {
      this.food = food;
   }

   public UserDay getUserDay() {
      return userDay;
   }

   public void setUserDay(UserDay userDay) {
      this.userDay = userDay;
   }
}
