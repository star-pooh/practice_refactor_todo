package org.example.practice_refactor_todo.reply.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.entity.Reply;
import org.example.practice_refactor_todo.common.entity.Todo;
import org.example.practice_refactor_todo.common.entity.User;
import org.example.practice_refactor_todo.reply.dto.ReplyResponseDto;
import org.example.practice_refactor_todo.reply.repository.ReplyRepository;
import org.example.practice_refactor_todo.todo.repository.TodoRepository;
import org.example.practice_refactor_todo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final ReplyRepository replyRepository;

  /**
   * 댓글 생성
   *
   * @param contents 댓글 내용
   * @param userId 사용자 ID
   * @param todoId 일정 ID
   * @return 저장된 댓글 정보
   */
  public ReplyResponseDto createReply(String contents, Long userId, Long todoId) {
    User foundUser = this.userRepository.findByIdElseOrThrow(userId);
    Todo foundTodo = this.todoRepository.findByIdOrElseThrow(todoId);

    Reply reply = Reply.of(contents, foundUser, foundTodo);
    Reply savedReply = this.replyRepository.save(reply);

    return ReplyResponseDto.toDto(savedReply);
  }

  /**
   * 댓글 전체 조회
   *
   * @return 조회된 댓글 정보
   */
  public List<ReplyResponseDto> findAllReply() {
    return this.replyRepository.findAll().stream().map(ReplyResponseDto::toDto).toList();
  }

  /**
   * 댓글 1건 조회
   *
   * @param id 댓글 ID
   * @return 조회된 댓글 정보
   */
  public ReplyResponseDto findById(Long id) {
    Reply foundReply = this.replyRepository.findByIdElseOrThrow(id);
    return ReplyResponseDto.toDto(foundReply);
  }

  /**
   * 댓글 수정
   *
   * @param id 댓글 ID
   * @param contents 댓글 내용
   */
  @Transactional
  public void updateReply(Long id, String contents) {
    Reply foundReply = this.replyRepository.findByIdElseOrThrow(id);
    foundReply.updateReply(contents);
  }

  /**
   * 댓글 삭제
   *
   * @param id 댓글 ID
   */
  public void deleteReply(Long id) {
    Reply foundReply = this.replyRepository.findByIdElseOrThrow(id);
    this.replyRepository.delete(foundReply);
  }
}
