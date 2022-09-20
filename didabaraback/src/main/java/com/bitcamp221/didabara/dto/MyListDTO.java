package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyListDTO {
  private Long id;
  private Long host;
  private String title;
  private String content;
  private String inviteCode;
  private String profileImageUrl;
  private LocalDate createdDate;
  private LocalDate modifiedDate;
  private Long count;

  public MyListDTO(final CategoryEntity categoryEntity) {
    this.id = categoryEntity.getId();
    this.host = categoryEntity.getHost();
    this.title = categoryEntity.getTitle();
    this.content = categoryEntity.getContent();
    this.inviteCode = categoryEntity.getInviteCode();
    this.profileImageUrl = categoryEntity.getProfileImageUrl();
    this.createdDate = categoryEntity.getCreatedDate();
    this.modifiedDate = categoryEntity.getModifiedDate();
  }
}
