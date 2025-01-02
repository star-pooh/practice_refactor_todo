package org.example.practice_refactor_todo.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {
  // 비밀번호
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  private final String password;

  // 이메일
  @Pattern(
      regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
      message = "이메일 형식이 올바르지 않습니다.")
  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  private final String email;

  private LoginRequestDto(String password, String email) {
    this.password = password;
    this.email = email;
  }
}
