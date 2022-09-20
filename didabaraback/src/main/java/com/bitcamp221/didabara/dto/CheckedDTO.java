package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.CheckedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDTO {

  private Long id;
  private Long user;
  private Long categoryItem;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  public CheckedDTO(CheckedEntity checkedEntity) {
    this.id = checkedEntity.getId();
    this.user = checkedEntity.getUser();
    this.categoryItem = checkedEntity.getCategoryItem();
    this.createdDate = checkedEntity.getCreatedDate();
    this.modifiedDate = checkedEntity.getModifiedDate();
  }

  public CheckedDTO(final Long userId, final Long categoryItemId) {
    this.user = userId;
    this.categoryItem = categoryItemId;
  }

  public static CheckedEntity toEntity(final CheckedDTO checkedDTO) {

    return CheckedEntity.builder()
            .id(checkedDTO.getId())
            .user(checkedDTO.getUser())
            .categoryItem(checkedDTO.getCategoryItem())
            .build();
  }
}
