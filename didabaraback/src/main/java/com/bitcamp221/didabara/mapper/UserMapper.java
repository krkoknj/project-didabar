package com.bitcamp221.didabara.mapper;

import com.bitcamp221.didabara.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface UserMapper {

  @Update("UPDATE user SET nickname=#{user.nickname}, password=#{user.password} WHERE username=#{user.username}")
  int updateUser(@Param("user") UserEntity userEntity);

  @Select("SELECT * FROM user WHERE username=#{email}")
  UserEntity selectUserIdByEmail(@Param("email") String email);

  @Select("SELECT user.username, emailconfig.auth_code " +
          "FROM user " +
          "JOIN emailconfig " +
          "ON user.id = emailconfig.id " +
          "WHERE emailconfig.auth_code = #{map.authCode} AND user.username = #{map.username}")
  Map<String, String> selectUsernameAndAuthCode(@Param("map") Map<String, String> map);

  @Select("SELECT username,emailconfig.auth_code FROM USER JOIN emailconfig ON USER.ID =emailconfig.ID" +
          "WHERE USER.USERNAME=#{username}")
  String findByUserName(@Param("username") String username);

  @Select("SELECT id FROM user ORDER BY id DESC LIMIT 1")
  Long lastOneIndex();

  @Select("SELECT username FROM user WHERE phone_number=#{ph}")
  String findUserPhoneNumber(@Param("ph") String phoneNum);
}