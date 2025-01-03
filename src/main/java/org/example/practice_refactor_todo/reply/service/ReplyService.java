package org.example.practice_refactor_todo.reply.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.entity.Reply;
import org.example.practice_refactor_todo.common.entity.Todo;
import org.example.practice_refactor_todo.common.entity.User;
import org.example.practice_refactor_todo.common.exception.CustomException;
import org.example.practice_refactor_todo.reply.dto.ReplyResponseDto;
import org.example.practice_refactor_todo.reply.repository.ReplyRepository;
import org.example.practice_refactor_todo.todo.repository.TodoRepository;
import org.example.practice_refactor_todo.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
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
    User foundUser =
        this.userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID 입니다. ID : " + userId));

    Todo foundTodo =
        this.todoRepository
            .findById(todoId)
            .orElseThrow(
                () ->
                    new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 일정 ID 입니다. ID : " + todoId));

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
    this.isExistReply(id);
    Reply foundReply = this.replyRepository.findById(id).get();

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
    this.isExistReply(id);
    Reply foundReply = this.replyRepository.findById(id).get();

    foundReply.updateReply(contents);
  }

  /**
   * 댓글 삭제
   *
   * @param id 댓글 ID
   */
  public void deleteReply(Long id) {
    this.isExistReply(id);
    Reply foundReply = this.replyRepository.findById(id).get();

    this.replyRepository.delete(foundReply);
  }

  /**
   * 댓글이 존재하는지 확인
   *
   * @param id 댓글 ID
   */
  private void isExistReply(Long id) {
    boolean isExist = this.replyRepository.existsById(id);

    if (!isExist) {
      throw new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글 ID 입니다. ID : " + id);
    }
  }
}
