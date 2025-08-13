package com.greengram.application.feed;

import com.greengram.application.feed.model.FeedGetDto;
import com.greengram.application.feed.model.FeedGetRes;
import com.greengram.application.feed.model.FeedPostReq;
import com.greengram.application.feed.model.FeedPostRes;
import com.greengram.application.feedcomment.FeedCommentMapper;
import com.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.greengram.application.feedcomment.model.FeedCommentItem;
import com.greengram.config.constants.ConstComment;
import com.greengram.config.util.ImgUploadManager;
import com.greengram.entity.Feed;
import com.greengram.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;
  private final ImgUploadManager imgUploadManager;
  private final FeedMapper feedMapper;
  private final FeedCommentMapper feedCommentMapper;
  private final ConstComment constComment;
  @Transactional
  public FeedPostRes postFeed(long signedUserId, FeedPostReq req, List<MultipartFile> pics) {
    User writerUser = new User();
    writerUser.setUserId(signedUserId);
    Feed feed = Feed.builder()
            .writerUser(writerUser)
            .location(req.getLocation())
            .contents(req.getContents())
            .build();

    feedRepository.save(feed); // feed 객체는 영속성을 갖는다

    List<String> fileNames = imgUploadManager.saveFeedPics(feed.getFeedId(), pics);

    feed.addFeedPics(fileNames);

    return new FeedPostRes(feed.getFeedId(), fileNames);
  }

  public List<FeedGetRes> getFeedList(FeedGetDto dto) {
    List<FeedGetRes> list = feedMapper.findAllLimitedTo(dto);
    // 각 피드에서 사진 가져오기, 댓글 가져오기(4개만)
    for(FeedGetRes feedGetRes : list) {
      feedGetRes.setPics(feedMapper.findAllPicByFeedId(feedGetRes.getFeedId()));
      // startIdx : 0, size : 4

      FeedCommentGetReq req = new FeedCommentGetReq(feedGetRes.getFeedId(), constComment.startIndex, constComment.needForViewCount);
      List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(req);
      boolean moreComment = commentList.size() > constComment.needForViewCount;
      FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes(moreComment, commentList);
      feedGetRes.setComments(feedCommentGetRes);

      if (moreComment) { // 마지막 댓글 삭제
        commentList.remove(commentList.size() - 1);
      }
    }
    return list;
  }
}
