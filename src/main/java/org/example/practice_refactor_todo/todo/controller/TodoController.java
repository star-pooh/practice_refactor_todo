package org.example.practice_refactor_todo.todo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.dto.ApiResponse;
import org.example.practice_refactor_todo.todo.dto.TodoCreateRequestDto;
import org.example.practice_refactor_todo.todo.dto.TodoPagingResponseDto;
import org.example.practice_refactor_todo.todo.dto.TodoResponseDto;
import org.example.practice_refactor_todo.todo.dto.TodoUpdateRequestDto;
import org.example.practice_refactor_todo.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  /**
   * 일정 생성 API
   *
   * @param dto 등록에 사용할 요청 데이터
   * @return 등록된 일정 정보
   */
  @PostMapping
  public ResponseEntity<ApiResponse<TodoResponseDto>> createTodo(
      @Valid @RequestBody TodoCreateRequestDto dto, HttpServletRequest httpServletRequest) {
    TodoResponseDto todoResponseDto =
        this.todoService.createTodo(dto.getUserId(), dto.getTitle(), dto.getContents());

    ApiResponse<TodoResponseDto> apiResponse =
        ApiResponse.success(HttpStatus.CREATED, "createTodo", todoResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
  }

  /**
   * 일정 전체 조회 API
   *
   * @param pageNum 페이지 번호
   * @param pageSize 페이지 크기
   * @return 조회된 일정 정보
   */
  @GetMapping
  public ResponseEntity<ApiResponse<List<TodoPagingResponseDto>>> findAllTodo(
      @RequestParam(defaultValue = "0") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    List<TodoPagingResponseDto> todoPagingResponseDtoList =
        this.todoService.findAllTodo(pageNum, pageSize);

    ApiResponse<List<TodoPagingResponseDto>> apiResponse =
        ApiResponse.success(HttpStatus.OK, "findAllTodo", todoPagingResponseDtoList);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * 일정 1건 조회 API
   *
   * @param id 일정 ID
   * @return 조회된 일정 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<TodoResponseDto>> findById(@PathVariable Long id) {
    TodoResponseDto todoResponseDto = this.todoService.findById(id);

    ApiResponse<TodoResponseDto> apiResponse =
        ApiResponse.success(HttpStatus.OK, "findByIdTodo", todoResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * 일정 수정 API
   *
   * @param id 일정 ID
   * @param dto 수정에 사용할 요청 데이터
   * @return 수정된 일정 정보
   */
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<TodoResponseDto>> updateTodo(
      @PathVariable Long id, @Valid @RequestBody TodoUpdateRequestDto dto) {
    this.todoService.updateTodo(id, dto.getTitle(), dto.getContents());
    // 수정일이 반영된 데이터가 필요하기 때문에 다시 조회를 실행
    TodoResponseDto todoResponseDto = this.todoService.findById(id);

    ApiResponse<TodoResponseDto> apiResponse =
        ApiResponse.success(HttpStatus.OK, "updateTodo", todoResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * 일정 삭제 API
   *
   * @param id 일정 ID
   * @return 삭제 결과
   */
  @DeleteMapping("/{id}")
  public ApiResponse<Void> deleteTodo(@PathVariable Long id) {
    this.todoService.deleteTodo(id);

    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.OK, "deleteTodo", null);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK).getBody();
  }
}
