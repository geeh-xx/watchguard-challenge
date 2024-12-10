package com.watchguard.challange.infrastructure.exceptions;

public class BusinessException extends RuntimeException {

  public BusinessException(String message, Exception error) {
    super(message, error);
  }
}
