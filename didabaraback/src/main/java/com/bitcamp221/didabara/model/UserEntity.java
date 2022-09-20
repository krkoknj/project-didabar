package com.bitcamp221.didabara.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false, length = 256)
  private String password;

  @Column(name = "nickname", nullable = false, length = 30, unique = true)
  private String nickname;

  @Column(name = "phone_number", length = 255)
  private String phoneNumber;

  @Column(name = "real_name", length = 255)
  private String realName;

  public void changePassword(String password) {
    this.password = password;
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

  public void changePhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}