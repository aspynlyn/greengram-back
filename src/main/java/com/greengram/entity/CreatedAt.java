package com.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass // 이 클래스를 상속하면 createdAt컬럼을 가지게 됨. 상속받을 수 있도록 하는 어노테이션
@EntityListeners(AuditingEntityListener.class) // 이벤트 연결, insert가 될 때 현재일시값 넣을 수 있도록 감시
public class CreatedAt {
  @CreatedDate
  @Column(nullable = false) // notnull
  private LocalDateTime createdAt;
}
