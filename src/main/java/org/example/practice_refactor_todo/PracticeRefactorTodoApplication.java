package org.example.practice_refactor_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PracticeRefactorTodoApplication {

  public static void main(String[] args) {
    SpringApplication.run(PracticeRefactorTodoApplication.class, args);
  }
}
