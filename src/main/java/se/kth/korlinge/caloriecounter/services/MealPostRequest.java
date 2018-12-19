package se.kth.korlinge.caloriecounter.services;


import java.sql.Date;

/**
 * Represents a POST request to create a new UserDay entity
 */
public class MealPostRequest {
   private int food;
   private Date date;
   private String username;

   public MealPostRequest() {

   }


   public MealPostRequest(int food) {
      this.food = food;
   }

   public MealPostRequest(int food, Date date) {
      this.food = food;
      this.date = date;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public int getFood() {
      return food;
   }

   public void setFood(int food) {
      this.food = food;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }
}

