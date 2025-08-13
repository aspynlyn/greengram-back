package com.greengram.application.feedcomment;


import com.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.greengram.application.feedcomment.model.FeedCommentItem;
import com.greengram.application.feedcomment.model.FeedCommentPostReq;
import com.greengram.config.constants.ConstComment;
import com.greengram.entity.Feed;
import com.greengram.entity.FeedComment;
import com.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
  private final FeedCommentRepository feedCommentRepository;
  private final FeedCommentMapper feedCommentMapper;
  private final ConstComment constComment;

  public long postFeedComment(long userId, FeedCommentPostReq req){
    User user = new User();
    user.setUserId(userId);

    Feed feed = Feed.builder()
            .feedId(req.getFeedId())
            .build();

    FeedComment feedComment = FeedComment.builder()
            .user(user)
            .feed(feed)
            .comment(req.getComment())
            .build();

    feedCommentRepository.save(feedComment);
    return feedComment.getFeedCommentId();
  }
  public FeedCommentGetRes getFeedList(FeedCommentGetReq req){
    List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(req);
    boolean moreComment = commentList.size() > (req.getSize());
    if (moreComment) {
      commentList.remove(commentList.size() - 1);
    }
    return new FeedCommentGetRes(moreComment, commentList);
  }

  public void deleteFeedComment(long userId, long feedCommentId) {
    FeedComment feedComment = feedCommentRepository.findById(feedCommentId).orElseThrow( () ->
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "feed_comment_id를 확인해 주세요."));
    // 본인이 작성한 댓글이 아니라면 exception 터트림 "본인이 작성한 댓글이 아닙니다."
    if (feedComment.getUser().getUserId() != userId) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글이 아닙니다.");
    }
    feedCommentRepository.deleteById(feedCommentId);
  }
}
