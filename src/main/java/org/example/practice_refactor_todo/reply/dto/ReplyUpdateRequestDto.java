package org.example.practice_refactor_todo.reply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplyUpdateRequestDto {
  @NotBlank(message = "내용은 필수 입력 값입니다.")
  @Size(max = 100, message = "100자 이내로 작성해주세요.")
  private final String contents;

  private ReplyUpdateRequestDto(String contents) {
    this.contents = contents;
  }
}
