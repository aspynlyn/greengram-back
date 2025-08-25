package com.green.greengram.application.feed.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedPostReq {
  @Size(max= 1_000, message = "contents는 1,000자 이하로 작성해주세요")
  private String contents;

  @Size(max = 30, message = "location은 30자 이하로 작성해주세요")
  private String location;
}
