package com.watchguard.challange.infrastructure.exceptions;

public class DirectoryException extends RuntimeException{

public DirectoryException(String message, Exception error){
  super(message, error);
}

}
