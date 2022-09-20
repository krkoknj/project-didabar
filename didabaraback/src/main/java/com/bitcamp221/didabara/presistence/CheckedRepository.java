package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.model.CheckedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckedRepository extends JpaRepository<CheckedEntity, Long> {

  void deleteByCategoryItem(@Param("categoryItemId") final Long categoryItemId);

//  String checkUserList = "SELECT new com.bitcamp221.didabara.dto.CheckUserDTO" +
//          "(u.nickname, ui.profileImageUrl)" +
//          "FROM UserEntity u INNER JOIN UserInfoEntity ui ON u.id = ui.id " +
//          "LEFT OUTER JOIN CheckedEntity ch ON ch.user = u.id " +
//          "WHERE ch.categoryItem = :categoryItemId IS NOT NULL AND u.id != :user";
//
//  @Query(value = checkUserList)
//  List<CheckUserDTO> findCheckUserList(@Param("categoryItemId") final Long categoryItemId, @Param("user") final Long user);
//
//
//  String unCheckUserList = "SELECT new com.bitcamp221.didabara.dto.CheckUserDTO" +
//          "(u.nickname, ui.profileImageUrl)" +
//          "FROM UserEntity u INNER JOIN UserInfoEntity ui ON u.id = ui.id " +
//          "LEFT OUTER JOIN CheckedEntity ch ON ch.user = u.id " +
//          "WHERE ch.categoryItem = :categoryItemId IS NULL AND u.id != :user";
//
//  @Query(value = unCheckUserList)
//  List<CheckUserDTO> findUnCheckUserList(@Param("categoryItemId") final Long categoryItemId, @Param("user") final Long user);

  @Query("SELECT ci.category, ci.title, ci.preview FROM CategoryItemEntity ci LEFT OUTER JOIN CheckedEntity ch " +
          "ON ci.id = ch.categoryItem WHERE ch.user = :user")
  List<CategoryItemEntity> findMyCheckList(@Param("user") final Long user);

  @Query("SELECT ci.category, ci.title, ci.preview FROM SubscriberEntity s INNER JOIN CategoryItemEntity ci ON s.category = ci.id " +
          "LEFT OUTER JOIN CheckedEntity ch ON ch.id IS NULL WHERE s.user = :userId")
  List<CategoryItemEntity> findMyUnCheckList(@Param("userId") final Long userId);

  boolean existsByUserAndCategoryItem(@Param("user") final Long user, @Param("categoryItem") final Long categoryItem);

  @Query("SELECT ch.id FROM CheckedEntity ch WHERE ch.categoryItem = :categoryItem AND ch.user = :user")
  Long findCheck(@Param("categoryItem") final Long categoryItem, @Param("user") final Long user);
}
