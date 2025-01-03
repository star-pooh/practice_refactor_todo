package org.example.practice_refactor_todo.user.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.dto.ApiResponse;
import org.example.practice_refactor_todo.user.dto.UserCreateRequestDto;
import org.example.practice_refactor_todo.user.dto.UserResponseDto;
import org.example.practice_refactor_todo.user.dto.UserUpdateRequestDto;
import org.example.practice_refactor_todo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * 사용자 생성 API
   *
   * @param dto 등록에 사용할 요청 데이터
   * @return 등록된 사용자 정보
   */
  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
      @Valid @RequestBody UserCreateRequestDto dto) {
    this.userService.checkRegisteredUser(dto.getEmail());

    UserResponseDto userResponseDto =
        this.userService.createUser(dto.getUsername(), dto.getPassword(), dto.getEmail());

    ApiResponse<UserResponseDto> apiResponse =
        ApiResponse.success(HttpStatus.CREATED, "createUser", userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
  }

  /**
   * 사용자 전체 조회 API
   *
   * @return 조회된 사용자 정보
   *     <p>사용자 정보가 1건도 없을 시, 빈 배열 반환
   */
  @GetMapping
  public ResponseEntity<ApiResponse<List<UserResponseDto>>> findAllUser() {
    List<UserResponseDto> userResponseDtoList = this.userService.findAllUser();

    ApiResponse<List<UserResponseDto>> apiResponse =
        ApiResponse.success(HttpStatus.OK, "findAllUser", userResponseDtoList);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * 사용자 1명 조회 API
   *
   * @param id 사용자 ID
   * @return 조회된 사용자 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<UserResponseDto>> findById(@PathVariable Long id) {
    UserResponseDto userResponseDto = this.userService.findById(id);

    ApiResponse<UserResponseDto> apiResponse =
        ApiResponse.success(HttpStatus.OK, "findByIdUser", userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * 사용자 정보 수정 API
   *
   * @param id 사용자 ID
   * @param dto 수정에 사용할 요청 데이터
   * @return 수정된 사용자 정보
   */
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserUpdateRequestDto dto) {
    UserResponseDto userResponseDto =
        this.userService.updateUser(id, dto.getUsername(), dto.getPassword(), dto.getEmail());

    ApiResponse<UserResponseDto> apiResponse =
        ApiResponse.success(HttpStatus.OK, "updateUser", userResponseDto);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * 사용자 삭제 API
   *
   * @param id 사용자 ID
   * @return 삭제 결과
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
    this.userService.deleteUser(id);

    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.OK, "deleteUser", null);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}
