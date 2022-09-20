package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.SubscriberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDTO {

  private Long id;
  private Long category;
  private Long user;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  public SubscriberDTO(SubscriberEntity subscriberEntity) {
    this.id = subscriberEntity.getId();
    this.category = subscriberEntity.getCategory();
    this.user = subscriberEntity.getUser();
    this.createdDate = subscriberEntity.getCreatedDate();
    this.modifiedDate = subscriberEntity.getModifiedDate();

  }

  public static SubscriberEntity toEntity(final SubscriberDTO subscriberDTO) {

    return SubscriberEntity.builder()
            .id(subscriberDTO.getId())
            .category(subscriberDTO.getCategory())
            .user(subscriberDTO.getUser())
            .build();
  }
}
