package org.example.practice_refactor_todo.todo.dto;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.example.practice_refactor_todo.common.entity.Todo;

@Getter
public class TodoResponseDto {

  // 일정 ID
  private final Long id;
  // 작성자명
  private final String username;
  // 일정 제목
  private final String title;
  // 일정 내용
  private final String contents;
  // 일정 작성일
  private final String createdDate;
  // 일정 수정일
  private final String modifiedDate;

  private TodoResponseDto(
      Long id,
      String username,
      String title,
      String contents,
      String createdDate,
      String modifiedDate) {
    this.id = id;
    this.username = username;
    this.title = title;
    this.contents = contents;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  /**
   * Dto로 변환
   *
   * @param todo 일정 정보
   * @return TodoResponseDto
   */
  public static TodoResponseDto toDto(Todo todo) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return new TodoResponseDto(
        todo.getId(),
        todo.getUser().getUsername(),
        todo.getTitle(),
        todo.getContents(),
        todo.getCreatedDate().format(dtf),
        todo.getModifiedDate().format(dtf));
  }
}
