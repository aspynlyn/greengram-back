package com.greengram.application.feedlike;

import com.greengram.entity.FeedLike;
import com.greengram.entity.FeedLikeIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLikeIds> {
  void deleteByIdFeedId(long feedId);
}
