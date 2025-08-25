package com.green.greengram.application.feed.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@ToString
public class FeedGetReq {
  @NotNull(message = "필수")
  @Positive
  private Integer page;

  @NotNull(message = "필수")
  @Min(value = 20, message = "20이상")
  @Max(value = 100, message = "100이하")
  private Integer rowPerPage;

  @Positive
  private Long profileUserId;

  public FeedGetReq(Integer page, @BindParam("row_per_page") Integer rowPerPage, @BindParam("profile_user_id") Long profileUserId) {
    this.page = page;
    this.rowPerPage = rowPerPage;
    this.profileUserId = profileUserId;
  }
}
