package com.greengram.application.feed.model;

import com.greengram.application.feedcomment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedGetRes {
  private long feedId;
  private String contents;
  private String location;
  private String createdAt;
  private long writerUserId;
  private String writerUid;
  private String writerNickName;
  private String writerPic;
  private int isLike; // 0: 싫어요피드 1: 좋아요피드
  private List<String> pics;

  private FeedCommentGetRes comments; // 댓글정보
}
