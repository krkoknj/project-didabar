package com.bitcamp221.didabara.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

  private Long id;
  private CategoryDTO category;
  private CategoryItemDTO categoryItem;
  private CategoryItemReplyDTO reply;
  private UserDTO user;
  private UserInfoDTO userInfo;

  private String title;
  private String content;
  private String nickname;
  private String profileImageUrl;
}
