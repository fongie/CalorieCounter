package se.kth.korlinge.caloriecounter.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDay {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @JsonIgnore
   @NotNull
   @ManyToOne
   private User user;

   @NotNull
   private double weight;

   @NotNull
   @Basic
   private Date date;

   public UserDay() {

   }

   public UserDay(@NotNull User user, @NotNull double weight, @NotNull Date date) {
      this.user = user;
      this.weight = weight;
      this.date = date;
   }

   public int getId() {
      return id;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
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
