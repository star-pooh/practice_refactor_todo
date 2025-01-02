package org.example.practice_refactor_todo.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.example.practice_refactor_todo.common.exception.ErrorDetails;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
  // 에러 타입
  private final HttpStatus httpStatus;
  // 에러 코드
  private final int code;
  // 에러 메시지
  private final String errorMessage;

  private ErrorResponse(HttpStatus httpStatus, int code, String errorMessage) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.errorMessage = errorMessage;
  }

  /**
   * Custom Exception에 대한 response 생성
   *
   * @param customException customException
   * @return response
   * @param <T> customException
   */
  public static <T extends ErrorDetails> Map<String, Object> responseFromCustomException(
      T customException) {
    Map<String, Object> response = new HashMap<>();

    response.put("code", customException.getHttpStatus().value());
    response.put("status", customException.getHttpStatus());
    response.put("message", customException.getErrorMessage());

    return response;
  }

  /**
   * 일반 Exception에 대한 response 생성
   *
   * @param e Exception
   * @param httpStatus HttpStatus
   * @return response
   */
  public static Map<String, Object> responseFromException(Exception e, HttpStatus httpStatus) {
    Map<String, Object> response = new HashMap<>();
    response.put("code", httpStatus.value());
    response.put("status", httpStatus);
    response.put("message", "예기치 못한 에러가 발생했습니다.");

    return response;
  }

  /**
   * 로그인 실패시 에러 메시지 생성
   *
   * @param response HttpServletResponse
   * @throws IOException IOException
   */
  public static void responseFromLoginFilter(HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    response
        .getWriter()
        .write(
            new ObjectMapper()
                .writeValueAsString(
                    Map.of(
                        "code",
                        HttpStatus.UNAUTHORIZED.value(),
                        "message",
                        "로그인 해주세요.",
                        "status",
                        HttpStatus.UNAUTHORIZED)));
  }
}
