package com.greengram.entity;

import com.greengram.config.enumcode.model.EnumUserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleIds implements Serializable {
  private Long userId;
  @Column(length= 2)
  private EnumUserRole roleCode;
}
