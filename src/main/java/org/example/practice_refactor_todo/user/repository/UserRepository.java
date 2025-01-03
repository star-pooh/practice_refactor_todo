package org.example.practice_refactor_todo.user.repository;

import java.util.Optional;
import org.example.practice_refactor_todo.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
