package com.greengram.application.feed;

import com.greengram.application.feed.model.*;
import com.greengram.config.model.ResultResponse;
import com.greengram.config.model.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
  private final FeedService feedService;

  private final int MAX_PIC_COUNT = 10;
  @PostMapping
  public ResultResponse<?> postFeed(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestPart FeedPostReq req, @RequestPart(name = "pic") List<MultipartFile> pics){
    if (pics.size() > MAX_PIC_COUNT) { // 사진 수량 제한
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("사진은 %d장까지 선택 가능합니다", MAX_PIC_COUNT));
    }
    log.info("signedUserId: {}", userPrincipal.getSignedUserId());
    log.info("req: {}", req);
    log.info("pics: {}", pics.size());
    FeedPostRes result = feedService.postFeed(userPrincipal.getSignedUserId(), req, pics);
    return new ResultResponse<>("피드 입력 완료", result);

  }

  // 페이징, 피드(사진, 댓글(3개만))
  // 현재는 피드 + 사진만 (N+1로 처리)
  @GetMapping
  public ResultResponse<?> getFeedList(@AuthenticationPrincipal UserPrincipal userPrincipal
          , @Valid @ModelAttribute FeedGetReq req){
    log.info("signedUserId: {}", userPrincipal.getSignedUserId());
    log.info("req: {}", req);
    FeedGetDto feedGetDto = FeedGetDto.builder()
            .signedUserId(userPrincipal.getSignedUserId())
            .startIdx((req.getPage() - 1) * req.getRowPerPage())
            .size(req.getRowPerPage())
            .build();
    List<FeedGetRes> result = feedService.getFeedList(feedGetDto);
    return new ResultResponse<>(String.format("rows: %d", result.size()), result);
  }
  // modelattribute는 setter나 생성자가 있어야 데이터 들어감
}