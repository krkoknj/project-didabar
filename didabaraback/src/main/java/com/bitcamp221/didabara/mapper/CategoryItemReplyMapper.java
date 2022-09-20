package com.bitcamp221.didabara.mapper;

import com.bitcamp221.didabara.dto.CheckUserDTO;
import com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryItemReplyMapper {

  @Select("SELECT cr.*, u.nickname, ui.profile_image_url " +
          "FROM category_item_reply AS cr " +
          "INNER JOIN user AS u ON cr.writer = u.id " +
          "INNER JOIN user_info AS ui ON cr.writer = ui.id WHERE cr.category_item_id = #{categoryItem}")
  List<ItemReplyAndUserDataDTO> findAllReplyData(@Param("categoryItem") final Long categoryItem);

  @Select("SELECT u.nickname, ui.profile_image_url FROM user AS u " +
          "INNER JOIN user_info AS ui ON u.id = ui.id " +
          "LEFT JOIN checked AS ch ON ch.user_id = u.id " +
          "WHERE ch.category_item_id = #{categoryItemId} IS NOT NULL AND u.id != #{user}")
  List<CheckUserDTO> findCheckUserList(@Param("categoryItemId") final Long categoryItemId, @Param("user") final Long user);

  @Select("SELECT u.nickname, ui.profile_image_url FROM user AS u " +
          "INNER JOIN user_info AS ui ON u.id = ui.id " +
          "LEFT JOIN checked AS ch ON ch.user_id = u.id " +
          "WHERE ch.category_item_id = #{categoryItemId} IS NULL AND u.id != #{user}")
  List<CheckUserDTO> findUnCheckUserList(@Param("categoryItemId") final Long categoryItemId, @Param("user") final Long user);

//  @Select("SELECT u.nickname, ui.profile_image_url, s.created_date " +
//          "FROM user AS u " +
//          "INNER JOIN user_info AS ui ON u.id = ui.id " +
//          "INNER JOIN subscriber AS s ON u.id = s.user_id " +
//          "WHERE s.category_id = #{category} AND s.user_id != #{user}")
//  List<CheckUserDTO> findSubscriberList(@Param("category") final Long category, @Param("user") final Long user);
}