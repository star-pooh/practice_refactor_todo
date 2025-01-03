package org.example.practice_refactor_todo.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class FilterException {
  public static void ofUnauthorizedException(HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    response
        .getWriter()
        .write(
            new ObjectMapper()
                .writeValueAsString(
                    Map.of(
                        "httpStatus",
                        HttpStatus.UNAUTHORIZED,
                        "httpStatusCode",
                        HttpStatus.UNAUTHORIZED.value(),
                        "message",
                        "로그인 해주세요.",
                        "status",
                        HttpStatus.UNAUTHORIZED)));
  }
}
