package org.example.practice_refactor_todo.todo.repository;

import java.util.Optional;
import org.example.practice_refactor_todo.common.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

  Page<Todo> findAllByOrderByModifiedDateDesc(Pageable pageable);

  Optional<Void> deleteAllByUserId(Long userId);
}
