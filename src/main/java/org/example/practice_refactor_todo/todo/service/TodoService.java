package org.example.practice_refactor_todo.todo.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.entity.Todo;
import org.example.practice_refactor_todo.common.entity.User;
import org.example.practice_refactor_todo.common.exception.CustomException;
import org.example.practice_refactor_todo.reply.repository.ReplyRepository;
import org.example.practice_refactor_todo.todo.dto.TodoPagingResponseDto;
import org.example.practice_refactor_todo.todo.dto.TodoResponseDto;
import org.example.practice_refactor_todo.todo.repository.TodoRepository;
import org.example.practice_refactor_todo.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;
  private final ReplyRepository replyRepository;

  /**
   * 일정 생성
   *
   * @param userId 작성자 ID
   * @param title 일정 제목
   * @param contents 일정 내용
   * @return 저장된 일정 정보
   */
  public TodoResponseDto createTodo(Long userId, String title, String contents) {
    Todo todo = Todo.of(title, contents);

    User findUser =
        this.userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID 입니다. ID : " + userId));
    todo.setUser(findUser);

    Todo savedTodo = this.todoRepository.save(todo);
    return TodoResponseDto.toDto(savedTodo);
  }

  /**
   * 전체 일정 조회
   *
   * @return 조회된 일정 정보
   */
  public List<TodoPagingResponseDto> findAllTodo(int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum, pageSize);
    Page<Todo> foundAllTodoList = this.todoRepository.findAllByOrderByModifiedDateDesc(pageable);

    return foundAllTodoList.stream().map(TodoPagingResponseDto::toDto).toList();
  }

  /**
   * 일정 1건 조회
   *
   * @param id 일정 ID
   * @return 조회된 일정 정보
   */
  public TodoResponseDto findById(Long id) {
    this.isExistTodo(id);
    Todo findTodo = this.todoRepository.findById(id).get();

    return TodoResponseDto.toDto(findTodo);
  }

  /**
   * 일정 수정
   *
   * @param id 일정 ID
   * @param title 일정 제목
   * @param contents 일정 내용
   */
  @Transactional
  public void updateTodo(Long id, String title, String contents) {
    this.isExistTodo(id);
    Todo findTodo = this.todoRepository.findById(id).get();

    findTodo.updateTodo(title, contents);
  }

  /**
   * 일정 삭제
   *
   * @param id 일정 ID
   */
  @Transactional
  public void deleteTodo(Long id) {
    this.isExistTodo(id);

    // 일정에 작성된 모든 댓글 삭제
    this.replyRepository.deleteAllByTodoId(id);

    Todo findTodo = this.todoRepository.findById(id).get();
    this.todoRepository.delete(findTodo);
  }

  /**
   * 일정이 존재하는지 확인
   *
   * @param id 일정 ID
   */
  private void isExistTodo(Long id) {
    boolean isExist = this.todoRepository.existsById(id);

    if (!isExist) {
      throw new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 일정 ID 입니다. ID : " + id);
    }
  }
}
