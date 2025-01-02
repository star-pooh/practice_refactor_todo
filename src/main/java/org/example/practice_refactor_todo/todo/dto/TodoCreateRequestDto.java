package org.example.practice_refactor_todo.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TodoCreateRequestDto {
  // 작성자 ID
  @NotNull(message = "사용자 ID는 필수 입력 값입니다.")
  private final Long userId;

  // 일정 제목
  @NotBlank(message = "제목은 필수 입력 값입니다.")
  @Size(max = 20, message = "제목은 20자 이내로 작성해주세요.")
  private final String title;

  // 일정 내용
  @NotBlank(message = "내용은 필수 입력 값입니다.")
  @Size(max = 200, message = "내용은 200자 이내로 작성해주세요.")
  private final String contents;

  private TodoCreateRequestDto(Long userId, String title, String contents) {
    this.userId = userId;
    this.title = title;
    this.contents = contents;
  }
}
