package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemDTO {

  private Long id;
  private Long category;
  private String itemPath;
  private String title;
  private String content;
  private LocalDate expiredDate;
  private LocalDate createdDate;
  private LocalDate modifiedDate;
  private String preview;

  public CategoryItemDTO(CategoryItemEntity categoryItemEntity) {
    this.id = categoryItemEntity.getId();
    this.category = categoryItemEntity.getCategory();
    this.itemPath = categoryItemEntity.getItemPath();
    this.title = categoryItemEntity.getTitle();
    this.content = categoryItemEntity.getContent();
    this.expiredDate = categoryItemEntity.getExpiredDate();
    this.createdDate = categoryItemEntity.getCreatedDate();
    this.modifiedDate = categoryItemEntity.getModifiedDate();
    this.preview = categoryItemEntity.getPreview();
  }

//  public CategoryItemDTO(Map map, int year, int month, int day) {
//    this.category = Long.valueOf(String.valueOf(map.get("categoryId")));
//    this.itemPath = String.valueOf(map.get("itemPath"));
//    this.content = String.valueOf(map.get("content"));
//    this.title = String.valueOf(map.get("title"));
//    this.expiredDate = LocalDate.of(year, month, day);
//  }

  public static CategoryItemEntity toEntity(final CategoryItemDTO categoryItemDTO) {

    return CategoryItemEntity.builder()
            .id(categoryItemDTO.getId())
            .category(categoryItemDTO.getCategory())
            .itemPath(categoryItemDTO.getItemPath())
            .title(categoryItemDTO.getTitle())
            .content(categoryItemDTO.getContent())
            .expiredDate(categoryItemDTO.getExpiredDate())
            .preview(categoryItemDTO.getPreview())
            .build();
  }
}