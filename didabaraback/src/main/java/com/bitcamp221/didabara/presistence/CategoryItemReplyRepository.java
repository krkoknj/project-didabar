package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO;
import com.bitcamp221.didabara.model.CategoryItemReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemReplyRepository extends JpaRepository<CategoryItemReplyEntity, Long> {

  String findAllReplyData = "SELECT new com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO" +
          "(cr.categoryItem, cr.writer, cr.id, cr.content, cr.createdDate, cr.modifiedDate, u.nickname, ui.profileImageUrl) " +
          "FROM CategoryItemReplyEntity cr " +
          "INNER JOIN UserEntity u ON cr.writer = u.id " +
          "INNER JOIN UserInfoEntity ui ON ui.id = cr.writer " +
          "WHERE cr.categoryItem = :categoryItem";

//  String findAllReplyData = "SELECT com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO(cr, u.nickname, ui.profile_image_url) FROM category_item_reply AS cr " +
//          "INNER JOIN user AS u On cr.writer = u.id " +
//          "INNER JOIN user_info AS ui ON cr.writer = ui.id " +
//          "WHERE cr.category_item_id = :categoryItem";

//  @Query("SELECT cr, u.nickname, ui.profileImageUrl FROM CategoryItemReplyEntity cr " +
//          "INNER JOIN UserEntity  u ON cr.writer = u.id " +
//          "INNER JOIN UserInfoEntity ui ON ui.id = cr.writer WHERE cr.categoryItem = :categoryItem")

  @Query(value = findAllReplyData)
  List<ItemReplyAndUserDataDTO> findAllReplyData(@Param(value = "categoryItem") final Long categoryItem);

  List<CategoryItemReplyEntity> findAllByWriter(@Param(value = "writer") final Long writer);

  @Query("SELECT r.writer FROM CategoryItemReplyEntity r WHERE r.id = :itemReplyId")
  Long findWriter(@Param(value = "itemReplyId") final Long itemReplyId);

  @Query("SELECT r.categoryItem FROM CategoryItemReplyEntity r WHERE r.id = :itemReplyId")
  Long findCategoryItemId(@Param(value = "itemReplyId") final Long itemReplyId);
}
