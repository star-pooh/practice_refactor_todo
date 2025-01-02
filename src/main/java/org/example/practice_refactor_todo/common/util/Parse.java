package org.example.practice_refactor_todo.common.util;

import java.util.List;
import org.springframework.validation.FieldError;

public class Parse {

  /**
   * validation 에러 메시지 변환
   *
   * @param fieldErrorList fieldErrorList
   * @return 에러 메시지만 모은 문자열
   */
  public static String toString(List<FieldError> fieldErrorList) {
    StringBuilder errorMessage = new StringBuilder();

    for (FieldError error : fieldErrorList) {
      errorMessage.append(error.getDefaultMessage()).append('\n');
    }

    return errorMessage.toString().trim();
  }
}
