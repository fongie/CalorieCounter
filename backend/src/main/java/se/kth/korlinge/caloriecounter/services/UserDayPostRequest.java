package se.kth.korlinge.caloriecounter.services;


import java.sql.Date;

/**
 * Represents a POST request to create a new UserDay entity
 */
public class UserDayPostRequest {
   private double weight;
   private Date date;
   private String username;

   public UserDayPostRequest() {

   }

   public UserDayPostRequest(double weight) {
      this.weight = weight;
   }

   public UserDayPostRequest(double weight, Date date) {
      this.weight = weight;
      this.date = date;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public double getWeight() {
      return weight;
   }

   public void setWeight(double weight) {
      this.weight = weight;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }
}
