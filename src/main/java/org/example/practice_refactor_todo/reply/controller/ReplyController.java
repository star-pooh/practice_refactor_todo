package org.example.practice_refactor_todo.reply.controller;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.exception.CustomValidationException;
import org.example.practice_refactor_todo.common.util.Parse;
import org.example.practice_refactor_todo.reply.dto.ReplyCreateRequestDto;
import org.example.practice_refactor_todo.reply.dto.ReplyResponseDto;
import org.example.practice_refactor_todo.reply.dto.ReplyUpdateRequestDto;
import org.example.practice_refactor_todo.reply.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

  private final ReplyService replyService;

  /**
   * 댓글 생성 API
   *
   * @param dto 등록에 사용할 요청 데이터
   * @param bindingResult validation 결과
   * @return 등록된 댓글 정보
   */
  @PostMapping
  public ResponseEntity<Map<String, Object>> createReply(
      @Valid @RequestBody ReplyCreateRequestDto dto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String errorMessage = Parse.toString(bindingResult.getFieldErrors());
      throw new CustomValidationException(HttpStatus.BAD_REQUEST, errorMessage);
    }

    Map<String, Object> response = new HashMap<>();

    ReplyResponseDto replyResponseDto =
        this.replyService.createReply(dto.getContents(), dto.getUserId(), dto.getTodoId());
    response.put("replyData", replyResponseDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * 댓글 전체 조회 API
   *
   * @return 조회된 댓글 정보
   *     <p>댓글 정보가 1건도 없을 시, 빈 배열 반환
   */
  @GetMapping
  public ResponseEntity<Map<String, List<ReplyResponseDto>>> findAllReply() {
    Map<String, List<ReplyResponseDto>> response = new HashMap<>();

    List<ReplyResponseDto> replyResponseDtoList = this.replyService.findAllReply();
    response.put("replyData", replyResponseDtoList);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * 댓글 1건 조회 API
   *
   * @param id 댓글 ID
   * @return 조회된 댓글 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, ReplyResponseDto>> findById(@PathVariable Long id) {
    Map<String, ReplyResponseDto> response = new HashMap<>();

    ReplyResponseDto replyResponseDto = this.replyService.findById(id);
    response.put("replyData", replyResponseDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * 댓글 수정 API
   *
   * @param id 댓글 ID
   * @param dto 수정에 사용할 요청 데이터
   * @param bindingResult validation 결과
   * @return 수정된 댓글 정보
   */
  @PatchMapping("/{id}")
  public ResponseEntity<Map<String, Object>> updateReply(
      @PathVariable Long id,
      @Valid @RequestBody ReplyUpdateRequestDto dto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String errorMessage = Parse.toString(bindingResult.getFieldErrors());
      throw new CustomValidationException(HttpStatus.BAD_REQUEST, errorMessage);
    }

    Map<String, Object> response = new HashMap<>();

    this.replyService.updateReply(id, dto.getContents());
    // 수정일이 반영된 데이터가 필요하기 때문에 다시 조회를 실행
    ReplyResponseDto replyResponseDto = this.replyService.findById(id);
    response.put("replyData", replyResponseDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * 댓글 삭제 API
   *
   * @param id 댓글 ID
   * @return 삭제 결과
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
    this.replyService.deleteReply(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
