package com.greengram.application.user;

import com.greengram.application.user.model.*;
import com.greengram.config.enumcode.model.EnumUserRole;
import com.greengram.config.model.JwtUser;
import com.greengram.config.util.ImgUploadManager;
import com.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ImgUploadManager imgUploadManager;

  @Transactional
  public void signUp(UserSignUpReq req, MultipartFile pic) {
    String hashedPassword = passwordEncoder.encode(req.getUpw());
    User user = new User();
    user.setNickName(req.getNickName());
    user.setUid(req.getUid());
    user.setUpw(hashedPassword);
    user.addUserRoles(req.getRoles());

    userRepository.save(user);

    if(pic != null) {
      String savedFileName = imgUploadManager.saveProfilePic(user.getUserId(), pic);
      user.setPic(savedFileName);
    }
  }

  public UserSignInDto signIn(UserSignInReq req){
    User user = userRepository.findByUid(req.getUid()); // 일치하는 아이디가 있는지 확인. null이 넘어오면 uid가 없음
    // passwordEncoder 내부에는 jbcrypt 객체가 있음
    if(user == null || !passwordEncoder.matches(req.getUpw(), user.getUpw())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디/비밀번호를 확인해 주세요");
    }

    // user 튜플을 가져왔는데 user_role에 저장되어 있는 데이터까지 가져올 수 있었던 건 양방향 관계 설정을 했기 때문
    // Fetch = fetchType.LAZY였을때 user.getUserRoles()는 JPA그래프 탐색(SELECT가 날아감)이라고 칭함

//    List<UserRole> roles2 = user.getUserRoles();
//    List<EnumUserRole> resultList = new ArrayList<>(roles2.size());
//
//    for(UserRole role : roles2) {
//      resultList.add(role.getUserRoleIds().getRoleCode());
//    }
//    아래 stream 머시기랑 같은 뜻

    List<EnumUserRole> roles = user.getUserRoles().stream().map(item -> item.getUserRoleIds().getRoleCode()).toList();
    log.info("roles: {}", roles);

    // security때문에 jwtUser(payload)에 담는 것
    JwtUser jwtUser = new JwtUser(user.getUserId(), roles);

    UserSignInRes userSignInRes = UserSignInRes.builder()
            .userId(user.getUserId()) // 프로필 사진 표시 때 사용
            .nickName(user.getNickName() == null ? user.getUid() : user.getNickName())
            .pic(user.getPic()) // 프로필 사진 표시 때 사용
            .build();

    return UserSignInDto.builder()
            .jwtUser(jwtUser) // 토큰 제작에 필요
            .userSignInRes(userSignInRes) // 프론트에 전달할 데이터
            .build();
  }
  public UserProfileGetRes getProfileUser(UserProfileGetDto dto) {
    return null;
  }
}
