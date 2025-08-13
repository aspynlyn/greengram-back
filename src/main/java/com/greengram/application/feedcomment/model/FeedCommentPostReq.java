package com.greengram.application.feedcomment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FeedCommentPostReq {
  @Positive
  private long feedId;

  @NotNull(message = "댓글 내용을 적어주세요")
  @Size(min = 1, max = 150, message = "댓글 내용은 150자 이하로 적어주세요")
  private String comment;
}
