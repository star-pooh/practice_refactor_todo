package org.example.practice_refactor_todo.common.exception;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.example.practice_refactor_todo.common.dto.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * CustomException 예외 처리
   *
   * @param e CustomException
   * @return ErrorResponse
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
    log.info("[{}] {} : {}", e.getStackTrace()[0], e.getHttpStatus(), e.getErrorMessage());

    ApiResponse<Void> errorResponse =
        ApiResponse.error(HttpStatus.BAD_REQUEST, e.getErrorMessage());
    return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
  }

  /**
   * RequestBody Validation 예외 처리
   *
   * @param e MethodArgumentNotValidException
   * @return ErrorResponse
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String errorMessage =
        e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("\n"));

    log.info("[{}] {} : {}", e.getStackTrace()[0], HttpStatus.BAD_REQUEST, errorMessage);

    ApiResponse<Void> errorResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Exception 예외 처리
   *
   * @param e Exception
   * @return ErrorResponse
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception e) {
    log.error(
        "[{}] {} : {}", e.getStackTrace()[0], HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

    ApiResponse<Void> errorResponse =
        ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
