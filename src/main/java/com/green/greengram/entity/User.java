package com.green.greengram.entity;

import com.green.greengram.config.enumcode.model.EnumUserRole;
import com.green.greengram.config.security.SignInProviderType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"uid", "provider_type"}
))
public class User extends UpdatedAt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false, length = 2)
  private SignInProviderType providerType;

  @Column(length = 30)
  private String nickName;

  @Column(length = 50, nullable = false)
  private String uid;

  @Column(length = 100)
  private String pic;

  @Column(length = 100, nullable = false)
  private String upw;

  // mappedBy는 관계설정을 무조건 안해도 되는 쪽에다가 작성
  // cascade : 자식이랑 나랑 모든 연결(내가 영속성되면 자식도 영속성, 내가 삭제되면 자식도 삭제)
  // orphanRemoval : userRoles에서 자식을 하나 제거하면(주소값 삭제) db에도 뺀 자식은 삭제처리됨
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<UserRole> userRoles = new ArrayList<>(1);

  public void addUserRoles(List<EnumUserRole> enumUserRole) {
    for (EnumUserRole e : enumUserRole) {
      UserRoleIds ids = new UserRoleIds(this.userId, e);
      UserRole userRole = new UserRole(ids, this);

      this.userRoles.add(userRole);
    }

  }
}
