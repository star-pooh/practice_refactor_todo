package org.example.practice_refactor_todo.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {
  // 비밀번호
  @Pattern(
      regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).*$",
      message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다.")
  @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  private final String password;

  // 이메일
  @Pattern(regexp = "^[a-zA-Z0-9_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식이 올바르지 않습니다.")
  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  private final String email;

  private LoginRequestDto(String password, String email) {
    this.password = password;
    this.email = email;
  }
}
