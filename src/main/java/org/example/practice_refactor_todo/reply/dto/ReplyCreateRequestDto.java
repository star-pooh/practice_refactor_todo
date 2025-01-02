package org.example.practice_refactor_todo.reply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplyCreateRequestDto {

  // 댓글 내용
  @NotBlank(message = "내용은 필수 입력 값입니다.")
  @Size(max = 100, message = "100자 이내로 작성해주세요.")
  private String contents;

  // 사용자 ID
  @NotNull(message = "사용자 ID는 필수 입력 값입니다.")
  private Long userId;

  // 일정 ID
  @NotNull(message = "일정 ID는 필수 입력 값입니다.")
  private Long todoId;
}
