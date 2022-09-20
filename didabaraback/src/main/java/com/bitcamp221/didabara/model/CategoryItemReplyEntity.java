package com.bitcamp221.didabara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_item_reply")
public class CategoryItemReplyEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "category_item_id", nullable = false)
  private Long categoryItem;

  @Column(name = "writer", nullable = false)
  private Long writer;

  @Column(name = "content", nullable = false)
  private String content;

  public void changeEntity(final CategoryItemReplyEntity itemReplyEntity) {
    this.id = itemReplyEntity.getId();
    this.categoryItem = itemReplyEntity.getCategoryItem();
    this.writer = itemReplyEntity.getWriter();
    this.content = itemReplyEntity.getContent();
  }
}
