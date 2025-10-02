package org.group;

public class InsufficientInventoryException extends RuntimeException {
  public InsufficientInventoryException(String message) {
    super(message);
  }
}
