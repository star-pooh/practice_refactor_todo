package org.example.practice_refactor_todo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomRepositoryException extends RuntimeException implements ErrorDetails {
  private final HttpStatus httpStatus;
  private final String errorMessage;

  public CustomRepositoryException(HttpStatus httpStatus, String errorMessage) {
    super(errorMessage);
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }
}
