package org.example.practice_refactor_todo.user.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.practice_refactor_todo.common.config.PasswordEncoder;
import org.example.practice_refactor_todo.common.entity.User;
import org.example.practice_refactor_todo.common.exception.CustomException;
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
   * 등록된 사용자인지 확인
   *
   * @param email 이메일
   */
  public void checkRegisteredUser(String email) {
    User foundUser = this.userRepository.findByEmail(email).orElse(null);

    if (Objects.isNull(foundUser)) {
      return;
    }

    throw new CustomException(HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다. email : " + email);
  }

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
    this.isExistUserById(id);
    User findUser = this.userRepository.findById(id).get();

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
    this.isExistUserById(id);
    User findUser = this.userRepository.findById(id).get();

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
    this.isExistUserById(id);

    // 사용자가 작성한 모든 댓글 삭제
    this.replyRepository.deleteAllByUserId(id);
    // 사용자가 작성한 모든 일정 삭제
    this.todoRepository.deleteAllByUserId(id);

    User findUser = this.userRepository.findById(id).get();
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
    this.isExistUserByEmail(email);

    User findUser = this.userRepository.findByEmail(email).get();
    boolean isMatchesPassword = this.passwordEncoder.matches(password, findUser.getPassword());

    if (!isMatchesPassword) {
      throw new CustomException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }

    return UserResponseDto.toDto(findUser);
  }

  /**
   * 유저가 존재하는지 ID로 확인
   *
   * @param id 유저 ID
   */
  private void isExistUserById(Long id) {
    boolean isExist = this.userRepository.existsById(id);

    if (!isExist) {
      throw new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID 입니다. ID : " + id);
    }
  }

  /**
   * 유저가 존재하는지 이메일로 확인
   *
   * @param email 이메일
   */
  private void isExistUserByEmail(String email) {
    boolean isExist = this.userRepository.existsByEmail(email);

    if (!isExist) {
      throw new CustomException(HttpStatus.UNAUTHORIZED, "존재하지 않는 이메일입니다. Email : " + email);
    }
  }
}
