package com.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class FeedComment extends UpdatedAt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long feedCommentId;

  @ManyToOne
  @JoinColumn(name = "feed_id", nullable = false)
  private Feed feed;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(length = 150, nullable = false)
  private String comment;
}
