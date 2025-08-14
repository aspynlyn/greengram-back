package com.greengram.application.user;

import com.greengram.application.user.model.*;
import com.greengram.config.jwt.JwtTokenManager;
import com.greengram.config.model.ResultResponse;
import com.greengram.config.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final JwtTokenManager jwtTokenManager;

  @PostMapping("/sign-up")
  // 파일업로드때문에 requestpart사용
  public ResultResponse<?> signUp(@Valid @RequestPart UserSignUpReq req, @RequestPart(required = false) MultipartFile pic){
    log.info("req: {}", req);
    log.info("pic: {}", pic != null ? pic.getOriginalFilename() : pic);
    userService.signUp(req, pic);
    return new ResultResponse<Integer>("",1 );

  }

  // response는 쿠키에 AT, RT를 담기위해 필요함 쿠키는 브라우저(사용자)에 저장
  @PostMapping("/sign-in")
  public ResultResponse<?> signIn(@Valid @RequestBody UserSignInReq req, HttpServletResponse response){
    log.info("sign req: {}", req);
    UserSignInDto userSignInDto = userService.signIn(req);
    jwtTokenManager.issue(response, userSignInDto.getJwtUser());
    return new ResultResponse<>("sign-in 성공",userSignInDto.getUserSignInRes());
  }

  @PostMapping("/sign-out")
  public ResultResponse<?> signOut(HttpServletResponse response){
    jwtTokenManager.signOut(response);
    return new ResultResponse<>("sign-out 성공",1);
  }

  @PostMapping("/reissue")
  public ResultResponse<?> reissue(HttpServletResponse response, HttpServletRequest request){
    jwtTokenManager.reissue(request, response);
    return new ResultResponse<>("AccessToken 재발행 성공",1);
  }
  @GetMapping("/profile")
  public ResultResponse<?> getProfileUser(@AuthenticationPrincipal UserPrincipal userPrincipal
          , @RequestParam("profile_user_id") long profileUserId) {
    log.info("profileUserId: {}", profileUserId);
    UserProfileGetDto dto = new UserProfileGetDto(userPrincipal.getSignedUserId(), profileUserId);
    UserProfileGetRes userProfileGetRes = userService.getProfileUser(dto);
    log.info("userProfileGetRes: {}", userProfileGetRes);
    return new ResultResponse<>("유저 정보 프로필", userProfileGetRes);
  }

  @PatchMapping("/profile/pic")
  public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestPart MultipartFile pic){
    String saveFileName = userService.patchProfilePic(userPrincipal.getSignedUserId(), pic);
    return new ResultResponse<>("프로필 유저 정보", saveFileName);
  }

}
