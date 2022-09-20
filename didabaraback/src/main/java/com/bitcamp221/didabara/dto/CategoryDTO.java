package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

  private Long id;
  private Long host;
  private String title;
  private String content;
  private String inviteCode;
  private String profileImageUrl;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  public CategoryDTO(final CategoryEntity categoryEntity) {
    this.id = categoryEntity.getId();
    this.host = categoryEntity.getHost();
    this.title = categoryEntity.getTitle();
    this.content = categoryEntity.getContent();
    this.inviteCode = categoryEntity.getInviteCode();
    this.profileImageUrl = categoryEntity.getProfileImageUrl();
    this.createdDate = categoryEntity.getCreatedDate();
    this.modifiedDate = categoryEntity.getModifiedDate();
  }

  public CategoryDTO(Optional<CategoryEntity> categoryEntity) {
    this.id = categoryEntity.get().getId();
    this.host = categoryEntity.get().getHost();
    this.title = categoryEntity.get().getTitle();
    this.content = categoryEntity.get().getContent();
    this.inviteCode = categoryEntity.get().getInviteCode();
    this.profileImageUrl = categoryEntity.get().getProfileImageUrl();
    this.createdDate = categoryEntity.get().getCreatedDate();
    this.modifiedDate = categoryEntity.get().getModifiedDate();
  }

  public static CategoryEntity toEntity(final CategoryDTO categoryDTO) {

    return CategoryEntity.builder()
            .id(categoryDTO.getId())
            .host(categoryDTO.getHost())
            .title(categoryDTO.getTitle())
            .content(categoryDTO.getContent())
            .inviteCode(categoryDTO.getInviteCode())
            .profileImageUrl(categoryDTO.getProfileImageUrl())
            .build();
  }
}
