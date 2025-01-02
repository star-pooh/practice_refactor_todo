package org.example.practice_refactor_todo.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.exception.CustomValidationException;
import org.example.practice_refactor_todo.common.util.Parse;
import org.example.practice_refactor_todo.login.dto.LoginRequestDto;
import org.example.practice_refactor_todo.user.dto.UserResponseDto;
import org.example.practice_refactor_todo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;

  /**
   * 사용자 로그인
   *
   * @param dto 로그인에 필요한 요청 데이터
   * @param bindingResult validation 결과
   * @param request HttpServletRequest
   * @return 로그인 결과
   */
  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(
      @Valid @RequestBody LoginRequestDto dto,
      BindingResult bindingResult,
      HttpServletRequest request) {
    if (bindingResult.hasErrors()) {
      String errorMessage = Parse.toString(bindingResult.getFieldErrors());
      throw new CustomValidationException(HttpStatus.BAD_REQUEST, errorMessage);
    }

    UserResponseDto userResponseDto =
        this.userService.validLoginUserInfo(dto.getPassword(), dto.getEmail());

    HttpSession session = request.getSession();
    session.setAttribute("loginUser", userResponseDto.getId());

    Map<String, String> response = new HashMap<>();

    String loginMessage = userResponseDto.getUsername() + "님이 로그인 하셨습니다.";
    response.put("message", loginMessage);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * 사용자 로그아웃
   *
   * @param request HttpServletRequest
   * @return 로그아웃 결과
   */
  @PostMapping("/logout")
  public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
    // 로그인하지 않으면 HttpSession을 null로 반환
    HttpSession session = request.getSession(false);

    if (!Objects.isNull(session)) {
      session.invalidate();
    }

    Map<String, String> response = new HashMap<>();
    response.put("message", "로그아웃이 완료되었습니다.");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
