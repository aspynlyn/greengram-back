package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeedPic extends CreatedAt {
  @EmbeddedId
  private FeedPicIds feedPicIds;

  @ManyToOne
  @MapsId("feedId")
  @JoinColumn(name = "feed_id")
  private Feed feed;
}
