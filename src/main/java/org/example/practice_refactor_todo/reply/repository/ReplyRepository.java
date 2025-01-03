package org.example.practice_refactor_todo.reply.repository;

import java.util.Optional;
import org.example.practice_refactor_todo.common.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  Optional<Void> deleteAllByTodoId(Long todoId);

  Optional<Void> deleteAllByUserId(Long userId);
}
