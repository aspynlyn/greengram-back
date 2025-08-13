package com.greengram.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FeedLikeIds implements Serializable {
  private Long feedId;
  private Long userId;
}
