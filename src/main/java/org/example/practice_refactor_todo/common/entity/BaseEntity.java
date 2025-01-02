package org.example.practice_refactor_todo.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  // 생성일
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

  // 수정일
  @LastModifiedDate private LocalDateTime modifiedDate;
}
