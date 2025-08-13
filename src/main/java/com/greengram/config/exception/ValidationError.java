package com.greengram.config.exception;

import lombok.*;
import org.springframework.validation.FieldError;

@Getter
@Builder
// builder패턴을 쓰려면 기본 생성자와 모든 멤버필드를 갖는 생성자 둘 다 있어야함
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ValidationError {
  private String field;
  private String message;

  public static ValidationError of(final FieldError fieldError) {
    return ValidationError.builder()
            .field(fieldError.getField())
            .message(fieldError.getDefaultMessage())
            .build();
  }

  @Override
  public String toString() {
    return String.format("field: %s, message: %s", field, message);
  }
}

