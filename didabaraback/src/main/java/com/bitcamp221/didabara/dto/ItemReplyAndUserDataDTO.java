package com.bitcamp221.didabara.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemReplyAndUserDataDTO {
  private Long categoryItem;
  private Long writer;
  private Long id;
  private String content;
  private LocalDate createdDate;
  private LocalDate modifiedDate;
  private String nickname;
  private String profileImageUrl;
}
