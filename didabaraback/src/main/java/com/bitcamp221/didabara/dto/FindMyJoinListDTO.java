package com.bitcamp221.didabara.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMyJoinListDTO {
  private Long id;
  private String title;
  private String content;
  private String categoryProfileImageUrl;
  private String nickname;
  private String userProfileImageUrl;
  private String filename;
}