package com.greengram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
  @EmbeddedId
  private UserRoleIds userRoleIds;

  // 관계설정
  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;


}
