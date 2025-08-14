package com.greengram.application.follow;

import com.greengram.entity.UserFollow;
import com.greengram.entity.UserFollowIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<UserFollow, UserFollowIds> {
}
