package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CategoryItemReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemReplyDTO {

  private Long id;
  private Long categoryItem;
  private Long writer;
  private String content;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  public CategoryItemReplyDTO(final CategoryItemReplyEntity itemReplyEntity) {
    this.id = itemReplyEntity.getId();
    this.categoryItem = itemReplyEntity.getCategoryItem();
    this.writer = itemReplyEntity.getWriter();
    this.content = itemReplyEntity.getContent();
    this.createdDate = itemReplyEntity.getCreatedDate();
    this.modifiedDate = itemReplyEntity.getModifiedDate();
  }

  public CategoryItemReplyDTO(final Long writer, final Long categoryItem, final String content) {
    this.writer = writer;
    this.categoryItem = categoryItem;
    this.content = content;
  }

  public static CategoryItemReplyEntity toEntity(final CategoryItemReplyDTO replyDTO) {

    return CategoryItemReplyEntity.builder()
            .id(replyDTO.getId())
            .categoryItem(replyDTO.getCategoryItem())
            .writer(replyDTO.getWriter())
            .content(replyDTO.getContent())
            .build();
  }
}
