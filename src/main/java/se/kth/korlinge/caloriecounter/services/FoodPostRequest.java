package se.kth.korlinge.caloriecounter.services;

/**
 * Represents a POST request to create a new Food entity
 */
public class FoodPostRequest {
   private String name;
   private Integer calories;
   private Integer protein;
   private String username;

   public FoodPostRequest() {

   }
   public FoodPostRequest(String name, Integer calories, Integer protein, String username) {
      this.name = name;
      this.calories = calories;
      this.protein = protein;
      this.username = username;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
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
