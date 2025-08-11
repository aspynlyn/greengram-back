package kr.co.wikibook.greengram.application.feed.model;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedGetDto {
  private long signedUserId;
  private int startIdx;
  private int size;
}
