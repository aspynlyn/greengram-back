package com.greengram.application.feedcomment;

import com.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.greengram.application.feedcomment.model.FeedCommentItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
  List<FeedCommentItem> findAllByFeedIdLimitedTo(FeedCommentGetReq req);
}
