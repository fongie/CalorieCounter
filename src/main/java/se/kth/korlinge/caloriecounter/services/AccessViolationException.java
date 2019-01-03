package se.kth.korlinge.caloriecounter.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when trying to access an entity that is not yours (the logged in user)
 */
@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class AccessViolationException extends RuntimeException {
   public AccessViolationException() {
      super("The entity you are trying to access does not belong to the current user.");
   }
}