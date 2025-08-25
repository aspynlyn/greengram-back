package com.green.greengram.application.feedlike;

import com.green.greengram.application.feedlike.model.FeedLikeToggleReq;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedLike;
import com.green.greengram.entity.FeedLikeIds;
import com.green.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedLikeService {
  private final FeedLikeRepository feedLikeRepository;

  public boolean toggle(Long signedUserId, FeedLikeToggleReq req){
    FeedLikeIds feedLikeIds = FeedLikeIds.builder()
            .feedId(req.getFeedId())
            .userId(signedUserId)
            .build();

    FeedLike feedLike = feedLikeRepository.findById(feedLikeIds).orElse(null);
    if(feedLike == null){
      // 싫어요 -> 좋아요
      Feed feed = Feed.builder()
              .feedId(req.getFeedId())
              .build();

      User user = new User();
      user.setUserId(signedUserId);

      FeedLike feedLikeSave = FeedLike.builder()
              .id(feedLikeIds)
              .userId(user)
              .feedId(feed)
              .build();

      feedLikeRepository.save(feedLikeSave);
      return true;
    }
    // 좋아요 -> 싫어요
    feedLikeRepository.delete(feedLike);
    return false;
  }
}
