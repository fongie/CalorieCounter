package se.kth.korlinge.caloriecounter.services;

import org.springframework.http.HttpStatus;
      import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when trying to use a field in an entity which does not exist.
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class FieldDoesNotExistException extends RuntimeException {
   public FieldDoesNotExistException() {
      super("A JSON key you tried to use does not exist for this entity.");
   }
   public FieldDoesNotExistException(String entity, String field) {
      super(errorMessage(entity, field));
   }
   private static String errorMessage(String entity, String field) {
      StringBuilder msg = new StringBuilder();
      msg.append("A JSON key you provided: '");
      msg.append(field);
      msg.append("' does not refer to a field that can be updated/created in the table '");
      msg.append(entity);
      msg.append("'. It either does not exist or is forbidden to change/create in this situation.");
      return msg.toString();
   }
}