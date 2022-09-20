package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.UserInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

  private Long id;
  private String job;
  private int role;
  private boolean ban;
  private String profileImageUrl;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  //xpt
  public UserInfoDTO(UserInfoEntity userInfoEntity) {
    this.id = userInfoEntity.getId();
    this.job = userInfoEntity.getJob();
    this.role = userInfoEntity.getRole();
    this.ban = userInfoEntity.isBan();
    this.profileImageUrl = userInfoEntity.getProfileImageUrl();
    this.createdDate = userInfoEntity.getCreatedDate();
    this.modifiedDate = userInfoEntity.getModifiedDate();
  }

  public static UserInfoEntity toEntity(final UserInfoDTO userInfoDTO) {

    return UserInfoEntity.builder()
            .id(userInfoDTO.getId())
            .job(userInfoDTO.getJob())
            .role(userInfoDTO.getRole())
            .ban(userInfoDTO.isBan())
            .profileImageUrl(userInfoDTO.getProfileImageUrl())
            .build();
  }
}
