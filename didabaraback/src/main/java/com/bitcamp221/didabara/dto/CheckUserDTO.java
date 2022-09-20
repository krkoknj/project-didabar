package com.bitcamp221.didabara.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserDTO {

  private String nickname;
  private String profileImageUrl;
  private String filename;
  private LocalDate createdDate;
}
