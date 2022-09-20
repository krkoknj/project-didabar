package com.bitcamp221.didabara.mapper;

import com.bitcamp221.didabara.model.EmailConfigEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmailConfigMapper {

  @Select("SELECT * FROM emailconfig WHERE " +
          "email=#{emailConfigEntity.id} AND auth_code=#{emailConfigEntity.authCode}")
  EmailConfigEntity selectEmailAndCode(@Param("emailConfigEntity") EmailConfigEntity emailConfigEntity);

  // 작성자 : 김남주
  // INSERT INTO emailconfig(id, auth_code) VALUES(id=1, auth_code='123312')
  // date 컬럼 디폴트값 없어서 직접 set CURRENT_TIMESTAMP
  @Insert("INSERT INTO emailconfig VALUES(#{id},current_date(),current_date(),#{code}, 0)")
  void saveUserIntoEmailconfig(@Param("id") Long id, @Param("code") String code);

  @Update("UPDATE emailconfig SET auth_code = #{code}, check_user = 0 WHERE id=#{id}")
  void updateUserIntoEmailconfig(@Param("id") Long id, @Param("code") String code);

  @Select("SELECT * FROM emailconfig WHERE id = #{id}")
  EmailConfigEntity selectEmailConfig(@Param("id") Long id);

}