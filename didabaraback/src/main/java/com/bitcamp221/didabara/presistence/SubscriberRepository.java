package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.dto.CheckUserDTO;
import com.bitcamp221.didabara.dto.FindMyJoinListDTO;
import com.bitcamp221.didabara.model.SubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Long> {

//  @Modifying
//  @Query("DELETE FROM SubscriberEntity s WHERE s.category = :categoryId AND s.user = :userId")
//  void deleteByCategoryIdAndUserId(@Param("categoryId") final Long categoryId, @Param("userId") final Long userId);

  void deleteByCategoryAndUser(@Param("category") final Long category, @Param("user") final Long user);

  @Query("SELECT u.nickname, ui.profileImageUrl FROM UserEntity u " +
          "INNER JOIN UserInfoEntity ui ON u.id = ui.id " +
          "INNER JOIN SubscriberEntity s ON s.user = u.id " +
          "WHERE s.category = :category")
  List<CheckUserDTO> findList(@Param("category") final Long category);

  boolean existsByCategoryAndUser(@Param("category") final Long category, @Param("user") final Long user);

  String findMyJoinList = "SELECT new com.bitcamp221.didabara.dto.FindMyJoinListDTO" +
          "(c.id, c.title, c.content, c.profileImageUrl, u.nickname, ui.profileImageUrl, ui.filename) " +
          "FROM SubscriberEntity s INNER JOIN CategoryEntity c ON s.user = :userId AND c.id = s.category " +
          "INNER JOIN UserEntity u ON u.id = c.host INNER JOIN UserInfoEntity ui ON u.id = ui.id";

  @Query(value = findMyJoinList)
  List<FindMyJoinListDTO> findMyJoinList(@Param("userId") final Long userId);

//  @Select("SELECT u.nickname, ui.profile_image_url, s.created_date " +
//          "FROM user AS u " +
//          "INNER JOIN user_info AS ui ON u.id = ui.id " +
//          "INNER JOIN subscriber AS s ON u.id = s.user_id " +
//          "WHERE s.category_id = #{category} AND s.user_id != #{user}")

  String findSubscriberList = "SELECT new com.bitcamp221.didabara.dto.CheckUserDTO" +
          "(u.nickname, ui.profileImageUrl, ui.filename, s.createdDate) " +
          "FROM UserEntity u " +
          "INNER JOIN UserInfoEntity ui ON u.id = ui.id " +
          "INNER JOIN SubscriberEntity s ON u.id = s.user " +
          "WHERE s.category = :category AND s.user != :user";

//  String findSubscriberList = "SELECT u.nickname, ui.profile_image_url" +
//          "FROM user AS u " +
//          "INNER JOIN user_info AS ui ON u.id = ui.id " +
//          "INNER JOIN subscriber AS s ON u.id = s.user_id " +
//          "WHERE s.category_id = :category AND s.user_id != :user";

  @Query(value = findSubscriberList)
  List<CheckUserDTO> findSubscriberList(@Param("category") final Long category, @Param("user") final Long user);
}