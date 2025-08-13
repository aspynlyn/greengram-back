package com.greengram.application.user.model;

import com.greengram.config.model.JwtUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignInDto {
  private UserSignInRes userSignInRes; // 응답용
  private JwtUser jwtUser; // JWT 발행때 사용
}
