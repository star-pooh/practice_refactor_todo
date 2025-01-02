package org.example.practice_refactor_todo.common.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

  // 사용자 ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 사용자 이름
  @Column(nullable = false)
  private String username;

  // 비밀번호
  @Column(nullable = false)
  private String password;

  // 이메일 (중복값 방지)
  @Column(nullable = false, unique = true)
  private String email;

  // 생성일
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

  private User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  protected User() {}

  /**
   * 사용자 정보 수정
   *
   * @param username 사용자 이름
   * @param password 비밀번호
   * @param email 이메일
   */
  public void updateUser(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  /**
   * User로 변환
   *
   * @param username 사용자 이름
   * @param password 비밀번호
   * @param email 이메일
   * @return User
   */
  public static User of(String username, String password, String email) {
    return new User(username, password, email);
  }
}
