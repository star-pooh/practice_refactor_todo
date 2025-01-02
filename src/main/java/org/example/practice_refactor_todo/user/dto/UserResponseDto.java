package org.example.practice_refactor_todo.user.dto;

import lombok.Getter;
import org.example.practice_refactor_todo.common.entity.User;

@Getter
public class UserResponseDto {
  // 사용자 ID
  private final Long id;
  // 사용자 이름
  private final String username;
  // 이메일
  private final String email;

  private UserResponseDto(Long id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }

  /**
   * Dto로 변환
   *
   * @param user 사용자 정보
   * @return UserResponseDto
   */
  public static UserResponseDto toDto(User user) {
    return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());
  }
}
