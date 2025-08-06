package kr.co.wikibook.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class UserFollowIds implements Serializable {
  private Long fromUserId;
  private Long toUserId;
}
