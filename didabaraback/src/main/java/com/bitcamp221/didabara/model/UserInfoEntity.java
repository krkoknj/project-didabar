package com.bitcamp221.didabara.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
@Data
public class UserInfoEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "job", length = 30)
  private String job;

  //  0은 일반 유저, 1은 관리자
  @Column(name = "role")
  @ColumnDefault("0")
  private int role;

  @Column(name = "ban")
  @ColumnDefault("false")
  private boolean ban;

  //  프로필 사진 컬럼
  @Column(name = "profile_image_url", length = 100)
  //  @ColumnDefault("기본 프로필 제공 이미지 경로")
  private String profileImageUrl;

  @Column(name = "file_name", length = 100)
  private String filename;

  @Column(name = "file_ori_name", length = 100)
  private String fileOriName;
}