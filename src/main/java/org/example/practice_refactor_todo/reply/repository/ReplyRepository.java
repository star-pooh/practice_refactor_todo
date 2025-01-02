package org.example.practice_refactor_todo.reply.repository;

import java.util.Optional;
import org.example.practice_refactor_todo.common.entity.Reply;
import org.example.practice_refactor_todo.common.exception.CustomRepositoryException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  default Reply findByIdElseOrThrow(Long id) {
    return findById(id)
        .orElseThrow(
            () ->
                new CustomRepositoryException(
                    HttpStatus.NOT_FOUND, "존재하지 않는 댓글 ID 입니다. ID : " + id));
  }

  Optional<Void> deleteAllByTodoId(Long todoId);

  Optional<Void> deleteAllByUserId(Long userId);
}
