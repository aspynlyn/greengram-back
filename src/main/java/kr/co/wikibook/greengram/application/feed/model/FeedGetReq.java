package kr.co.wikibook.greengram.application.feed.model;

import jakarta.validation.constraints.*;
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

  public FeedGetReq(Integer page, @BindParam("row_per_page") Integer rowPerPage) {
    this.page = page;
    this.rowPerPage = rowPerPage;
  }
}
