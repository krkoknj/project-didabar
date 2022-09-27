package com.bitcamp221.didabara.mapper;

import com.bitcamp221.didabara.dto.UserDTO;
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

  @Select("SELECT * from user WHERE id =#{id}")
  UserDTO findByIdUser(@Param("id") String id);


  @Select("SELECT * FROM user WHERE username=#{phone} AND real_name=#{realName}")
  UserDTO findByPhoneAndrealName(@Param("phone") String userPhoneNum, @Param("realName") String realName);

  @Select("SELECT * FROM user WHERE username=#{phone} AND real_name=#{realName} AND username=#{userName}")
  UserDTO findByPhoneNumberAndRealNameAndUsername(@Param("phone") String userPhoneNum, @Param("realName") String realName, @Param("userName") String username);

  @Select("SELECT * FROM user WHERE phone_number=#{phone}")
  UserDTO findByPhoneNumber(@Param("phone") String phoneNum);

  @Update("UPDATE user SET password=#{pwd} WHERE id =#{id}")
  int updateUserPassword(@Param("pwd") String encodePwd, @Param("id") Long id);
}