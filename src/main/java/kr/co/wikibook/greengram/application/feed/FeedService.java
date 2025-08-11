package kr.co.wikibook.greengram.application.feed;

import kr.co.wikibook.greengram.application.feed.model.FeedGetRes;
import kr.co.wikibook.greengram.application.feed.model.FeedPostReq;
import kr.co.wikibook.greengram.config.util.ImgUploadManager;
import kr.co.wikibook.greengram.entity.Feed;
import kr.co.wikibook.greengram.entity.User;
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

  public void postFeed(long signedUserId, FeedPostReq req, List<MultipartFile> pics) {
    User writerUser = new User();
    writerUser.setUserId(signedUserId);
    Feed feed = Feed.builder()
            .writerUser(writerUser)
            .location(req.getLocation())
            .contents(req.getContents())
            .build();

    feedRepository.save(feed);

    List<String> fileNames = imgUploadManager.saveFeedPics(feed.getFeedId(), pics);

    feed.addFeedPics(fileNames);

//    return new FeedGetRes(feed.getFeedId())
  }
}
