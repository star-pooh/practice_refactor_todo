package org.example.practice_refactor_todo.user.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.config.PasswordEncoder;
import org.example.practice_refactor_todo.common.entity.User;
import org.example.practice_refactor_todo.common.exception.CustomRepositoryException;
import org.example.practice_refactor_todo.reply.repository.ReplyRepository;
import org.example.practice_refactor_todo.todo.repository.TodoRepository;
import org.example.practice_refactor_todo.user.dto.UserResponseDto;
import org.example.practice_refactor_todo.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final TodoRepository todoRepository;
  private final ReplyRepository replyRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 사용자 생성
   *
   * @param username 사용자 이름
   * @param password 비밀번호
   * @param email 이메일
   * @return 저장된 사용자 정보
   */
  public UserResponseDto createUser(String username, String password, String email) {
    String encodedPassword = this.passwordEncoder.encode(password);

    User user = User.of(username, encodedPassword, email);
    User savedUser = this.userRepository.save(user);

    return UserResponseDto.toDto(savedUser);
  }

  /**
   * 사용자 전체 조회
   *
   * @return 조회된 사용자 정보
   */
  public List<UserResponseDto> findAllUser() {
    return this.userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
  }

  /**
   * 사용자 1명 조회
   *
   * @param id 사용자 ID
   * @return 조회된 사용자 정보
   */
  public UserResponseDto findById(Long id) {
    User findUser = this.userRepository.findByIdElseOrThrow(id);
    return UserResponseDto.toDto(findUser);
  }

  /**
   * 사용자 정보 수정
   *
   * @param id 사용자 ID
   * @param username 사용자 이름
   * @param password 비밀번호
   * @param email 이메일
   * @return 수정된 사용자 정보
   */
  @Transactional
  public UserResponseDto updateUser(Long id, String username, String password, String email) {
    User findUser = this.userRepository.findByIdElseOrThrow(id);

    String encodedPassword = this.passwordEncoder.encode(password);
    findUser.updateUser(username, encodedPassword, email);

    return UserResponseDto.toDto(findUser);
  }

  /**
   * 사용자 삭제
   *
   * @param id 사용자 ID
   */
  @Transactional
  public void deleteUser(Long id) {
    // 사용자가 작성한 모든 댓글 삭제
    this.replyRepository.deleteAllByUserId(id);
    // 사용자가 작성한 모든 일정 삭제
    this.todoRepository.deleteAllByUserId(id);

    User findUser = this.userRepository.findByIdElseOrThrow(id);
    this.userRepository.delete(findUser);
  }

  /**
   * 사용자 조회 (로그인용)
   *
   * @param password 비밀번호
   * @param email 이메일
   * @return 조회된 사용자 정보
   */
  public UserResponseDto validLoginUserInfo(String password, String email) {
    User findUser = this.userRepository.findByEmailElseOrThrow(email);
    boolean isMatchesPassword = this.passwordEncoder.matches(password, findUser.getPassword());

    if (!isMatchesPassword) {
      throw new CustomRepositoryException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }

    return UserResponseDto.toDto(findUser);
  }
}
