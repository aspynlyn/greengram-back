package kr.co.wikibook.greengram.application.feed;

import jakarta.validation.Valid;
import kr.co.wikibook.greengram.application.feed.model.FeedGetDto;
import kr.co.wikibook.greengram.application.feed.model.FeedGetReq;
import kr.co.wikibook.greengram.application.feed.model.FeedPostReq;
import kr.co.wikibook.greengram.config.model.ResultResponse;
import kr.co.wikibook.greengram.config.model.UserPrincipal;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
  private final FeedService feedService;

  @PostMapping
  public ResultResponse<?> postFeed(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestPart FeedPostReq req, @RequestPart(name = "pic") List<MultipartFile> pics){
    log.info("signedUserId: {}", userPrincipal.getSignedUserId());
    log.info("req: {}", req);
    log.info("pics: {}", pics.size());
    feedService.postFeed(userPrincipal.getSignedUserId(), req, pics);
    return new ResultResponse<>("피드 입력 완료", null);

  }

  // 페이징, 피드(사진, 댓글(3개만))
  // 현재는 피드 + 사진만 (N+1로 처리)
  @GetMapping
  public ResultResponse<?> getFeedList(@AuthenticationPrincipal UserPrincipal userPrincipal
          ,@Valid @ModelAttribute FeedGetReq req){
    log.info("signedUserId: {}", userPrincipal.getSignedUserId());
    log.info("req: {}", req);
    FeedGetDto feedGetDto = FeedGetDto.builder()
            .signedUserId(userPrincipal.getSignedUserId())
            .startIdx((req.getPage() - 1) * req.getRowPerPage())
            .size(req.getRowPerPage())
            .build();
    return null;
  }
  // modelattribute는 setter나 생성자가 있어야 데이터 들어감
}
