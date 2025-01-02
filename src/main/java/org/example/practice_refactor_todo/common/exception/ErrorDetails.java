package org.example.practice_refactor_todo.common.exception;

import org.springframework.http.HttpStatus;

/**
 * CustomException용 인터페이스
 *
 * <p>모든 Exception에서 getHttpStatus(), getErrorMessage를 할 수 없다고 하여 생성
 */
public interface ErrorDetails {
  HttpStatus getHttpStatus();

  String getErrorMessage();
}
