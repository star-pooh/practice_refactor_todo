package org.example.practice_refactor_todo.common.exception;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.example.practice_refactor_todo.common.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * CustomValidationException 처리
   *
   * @param e CustomValidationException
   * @return 에러 정보
   */
  @ExceptionHandler(CustomValidationException.class)
  public ResponseEntity<Map<String, Object>> handleCustomValidationException(
      CustomValidationException e) {
    Map<String, Object> response = ErrorResponse.responseFromCustomException(e);

    log.error("[handleCustomValidationException] {} : {} ", e.getHttpStatus(), e.getErrorMessage());

    return ResponseEntity.status(e.getHttpStatus()).body(response);
  }

  /**
   * CustomRepositoryException 처리
   *
   * @param e CustomRepositoryException
   * @return 에러 정보
   */
  @ExceptionHandler(CustomRepositoryException.class)
  public ResponseEntity<Map<String, Object>> handleCustomRepositoryException(
      CustomRepositoryException e) {
    Map<String, Object> response = ErrorResponse.responseFromCustomException(e);

    log.error("[handleCustomRepositoryException] {} : {} ", e.getHttpStatus(), e.getErrorMessage());

    return ResponseEntity.status(e.getHttpStatus()).body(response);
  }

  /**
   * Exception 처리
   *
   * @param e Exception
   * @return 에러 정보
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleException(Exception e) {
    Map<String, Object> response =
        ErrorResponse.responseFromException(e, HttpStatus.INTERNAL_SERVER_ERROR);

    log.error("[handleException] {} : {} ", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
