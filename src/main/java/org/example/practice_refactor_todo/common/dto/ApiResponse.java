package org.example.practice_refactor_todo.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
  private final HttpStatus httpStatus;
  private final int httpStatusCode;
  private final String message;
  private final T responseData;

  private ApiResponse(HttpStatus httpStatus, int httpStatusCode, String message, T responseData) {
    this.httpStatus = httpStatus;
    this.httpStatusCode = httpStatusCode;
    this.message = message;
    this.responseData = responseData;
  }

  public static <T> ApiResponse<T> success(HttpStatus httpStatus, String message, T responseData) {
    return new ApiResponse<>(httpStatus, httpStatus.value(), message, responseData);
  }

  public static <T> ApiResponse<T> error(HttpStatus httpStatus, String message) {
    return new ApiResponse<>(httpStatus, httpStatus.value(), message, null);
  }
}
