package org.example.practice_refactor_todo.user.repository;

import java.util.Optional;
import org.example.practice_refactor_todo.common.entity.User;
import org.example.practice_refactor_todo.common.exception.CustomRepositoryException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface UserRepository extends JpaRepository<User, Long> {
  default User findByIdElseOrThrow(Long id) {
    return findById(id)
        .orElseThrow(
            () ->
                new CustomRepositoryException(
                    HttpStatus.NOT_FOUND, "존재하지 않는 유저 ID 입니다. ID : " + id));
  }

  Optional<User> findByEmail(String email);

  default User findByEmailElseOrThrow(String email) {
    return findByEmail(email)
        .orElseThrow(
            () ->
                new CustomRepositoryException(
                    HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자이거나 이메일이 일치하지 않습니다."));
  }
}
