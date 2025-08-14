package com.greengram.application.user;

import com.greengram.application.user.model.UserProfileGetDto;
import com.greengram.application.user.model.UserProfileGetRes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  UserProfileGetRes findProfileByUserId(UserProfileGetDto dto);
}
