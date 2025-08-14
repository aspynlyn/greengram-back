package com.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class UserFollow extends CreatedAt {
  @EmbeddedId
  private UserFollowIds userFollowIds;
  // 관계설정
  @ManyToOne
  @MapsId("fromUserId")
  @JoinColumn(name = "from_user_id")
  private User fromUserId;

  @ManyToOne
  @MapsId("toUserId")
  @JoinColumn(name = "to_user_id")
  private User toUserId;
}
