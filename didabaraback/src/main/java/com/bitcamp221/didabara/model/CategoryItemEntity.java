package com.bitcamp221.didabara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_item")
public class CategoryItemEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "category_id", nullable = false)
  private Long category;

  @Column(name = "item_path", nullable = false)
  private String itemPath;

  @Column(name = "title", nullable = false, length = 30)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Column(name = "expired_date", nullable = false)
  private LocalDate expiredDate;

  @Column(name = "preview", nullable = false)
  private String preview;

  public void changeEntity(final CategoryItemEntity categoryItemEntity) {
    this.id = categoryItemEntity.getId();
    this.category = categoryItemEntity.getCategory();
    this.itemPath = categoryItemEntity.getItemPath();
    this.title = categoryItemEntity.getTitle();
    this.content = categoryItemEntity.getContent();
    this.expiredDate = categoryItemEntity.getExpiredDate();
    this.preview = categoryItemEntity.getPreview();
  }
}