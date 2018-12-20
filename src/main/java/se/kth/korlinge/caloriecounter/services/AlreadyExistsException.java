package se.kth.korlinge.caloriecounter.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when trying to post something that already exists.
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {
   public AlreadyExistsException() {
      super("The entity you are trying to add already exists.");
   }
   public AlreadyExistsException(String table, String key, Object value) {
      super(errorMessage(table, key, value));
   }
   private static String errorMessage(String table, String key, Object value) {
      StringBuilder msg = new StringBuilder();
      msg.append("You are trying to post a new '");
      msg.append(key);
      msg.append("' key with the value '");
      msg.append(value.toString());
      msg.append("' as a new '");
      msg.append(table);
      msg.append("'. This '");
      msg.append(table);
      msg.append("' entity already exists in the database, use that instead!");
      return msg.toString();
   }
}