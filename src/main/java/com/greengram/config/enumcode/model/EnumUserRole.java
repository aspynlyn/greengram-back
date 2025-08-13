package com.greengram.config.enumcode.model;

import com.greengram.config.enumcode.AbstractEnumCodeConverter;
import com.greengram.config.enumcode.EnumMapperType;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumUserRole implements EnumMapperType {
  USER_1("01", "유저1")
  , USER_2("02", "유저2")
  , USER_3("03", "유저3")
  , USER_4("04", "매니저")
  , USER_5("05", "관리자")
  ;

  private final String code;
  private final String value;

  @Converter(autoApply = true)
  public static class CodeConverter extends AbstractEnumCodeConverter<EnumUserRole> {
    public CodeConverter() {
      super(EnumUserRole.class, false);
    }
  }
}
