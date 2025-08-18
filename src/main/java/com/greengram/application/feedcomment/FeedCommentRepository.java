package com.greengram.application.feedcomment;

import com.greengram.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
  void deleteByFeedFeedId(long feedId);
}
