package org.example.practice_refactor_todo.reply.dto;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.example.practice_refactor_todo.common.entity.Reply;

@Getter
public class ReplyResponseDto {

  // 댓글 ID
  private final Long id;
  // 댓글 내용
  private final String contents;
  // 사용자 이름
  private final String username;
  // 일정 제목
  private final String todoTitle;
  // 댓글 작성일
  private final String createdDate;
  // 댓글 수정일
  private final String modifiedDate;

  private ReplyResponseDto(
      Long id,
      String contents,
      String username,
      String todoTitle,
      String createdDate,
      String modifiedDate) {
    this.id = id;
    this.contents = contents;
    this.username = username;
    this.todoTitle = todoTitle;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  /**
   * Dto로 변환
   *
   * @param reply 댓글 정보
   * @return ReplyResponseDto
   */
  public static ReplyResponseDto toDto(Reply reply) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    return new ReplyResponseDto(
        reply.getId(),
        reply.getContents(),
        reply.getUser().getUsername(),
        reply.getTodo().getTitle(),
        reply.getCreatedDate().format(dtf),
        reply.getModifiedDate().format(dtf));
  }
}
