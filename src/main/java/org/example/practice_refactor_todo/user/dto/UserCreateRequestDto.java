package org.example.practice_refactor_todo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
  // 사용자 이름
  @Pattern(regexp = "^[A-Za-z0-9가-힣\\s]+$", message = "사용자 이름에는 특수문자가 포함될 수 없습니다.")
  @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
  private final String username;

  // 비밀번호
  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  @Size(min = 4, max = 10, message = "비밀번호는 4자 이상 10자 이내로 입력해주세요.")
  private final String password;

  // 이메일
  @Pattern(
      regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
      message = "이메일 형식이 올바르지 않습니다.")
  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  private final String email;

  private UserCreateRequestDto(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
