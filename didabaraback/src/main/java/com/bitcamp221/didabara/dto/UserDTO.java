package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  private Long id;
  private String username;
  private String password;
  private String nickname;

  private String token;
  private String phoneNumber;

  private String realName;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  //  DB에서 꺼내온 Entity를 new UserDTO(userEntity)를 하여서
//  DTO로 변환해서 사용!


  public UserDTO(String username, String nickname, String password) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
  }

  public UserDTO(UserEntity user) {
  }

  public UserEntity toEntity() {
    return UserEntity.builder()
            .id(id)
            .username(username)
            .password(passwordEncoder.encode(password))
            .nickname(nickname)
            .realName(realName)
            .phoneNumber(phoneNumber)
            .build();
  }
}
