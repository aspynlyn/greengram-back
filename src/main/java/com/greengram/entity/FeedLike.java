package com.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FeedLike extends CreatedAt {
  @EmbeddedId
  private FeedLikeIds id;

  @ManyToOne
  @MapsId("feedId")
  @JoinColumn(name = "feed_id")
  private Feed feedId;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User userId;

}
