package org.example.practice_refactor_todo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
  // 사용자 이름
  @Pattern(regexp = "^[A-Za-z0-9가-힣\\s]+$", message = "사용자 이름에는 특수문자가 포함될 수 없습니다.")
  @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
  private final String username;

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

  private UserUpdateRequestDto(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
