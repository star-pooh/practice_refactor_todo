package org.example.practice_refactor_todo.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.dto.ApiResponse;
import org.example.practice_refactor_todo.login.dto.LoginRequestDto;
import org.example.practice_refactor_todo.user.dto.UserResponseDto;
import org.example.practice_refactor_todo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
   * @param request HttpServletRequest
   * @return 로그인 결과
   */
  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Void>> login(
      @Valid @RequestBody LoginRequestDto dto, HttpServletRequest request) {
    UserResponseDto userResponseDto =
        this.userService.validLoginUserInfo(dto.getPassword(), dto.getEmail());

    HttpSession session = request.getSession();
    session.setAttribute("loginUser", userResponseDto.getId());

    String loginMessage = userResponseDto.getUsername() + "님이 로그인 하셨습니다.";
    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.CREATED, loginMessage, null);

    return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
  }

  /**
   * 사용자 로그아웃
   *
   * @param request HttpServletRequest
   * @return 로그아웃 결과
   */
  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
    // 로그인하지 않으면 HttpSession을 null로 반환
    HttpSession session = request.getSession(false);

    if (!Objects.isNull(session)) {
      session.invalidate();
    }

    String logoutMessage = "로그아웃이 완료되었습니다.";
    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.OK, logoutMessage, null);

    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}
