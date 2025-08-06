package kr.co.wikibook.greengram.application.user;

import kr.co.wikibook.greengram.config.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @PostMapping("/sign-up")
  public ResultResponse<?> signUp(){
    return new ResultResponse<Integer>("",1 );
  }
}
