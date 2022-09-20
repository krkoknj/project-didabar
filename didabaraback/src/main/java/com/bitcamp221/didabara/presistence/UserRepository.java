package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByNickname(String nickname);

  UserEntity findByPhoneNumberAndRealName(String phoneNum, String realName);

  UserEntity findByPhoneNumberAndRealNameAndUsername(String phoneNum, String realName, String username);

  UserEntity findByUsernameAndPassword(String username, String password);

  UserEntity findByPhoneNumber(String phoneNum);
}
