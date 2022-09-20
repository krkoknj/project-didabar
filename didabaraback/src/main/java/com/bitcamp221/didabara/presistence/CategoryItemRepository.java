package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItemEntity, Long> {

  List<CategoryItemEntity> findAllByCategory(@Param("category") final Long category);

  @Query("SELECT c.category FROM CategoryItemEntity c WHERE c.id = :categoryItemId")
  Long findCategoryId(@Param(value = "categoryItemId") final Long categoryItemId);

  Long countByCategory(@Param(value = "categoryId") final Long categoryId);

  @Query("SELECT c.itemPath FROM CategoryItemEntity c WHERE c.id = :id")
  String findUrl(@Param("id") final Long id);

  @Query("SELECT c FROM CategoryItemEntity c LEFT OUTER JOIN SubscriberEntity su ON su.category = c.category WHERE su.user = :user")
  List<CategoryItemEntity> findAllItem(@Param(value = "user") final Long user);

  @Query("SELECT ci FROM CategoryItemEntity ci INNER JOIN CategoryEntity c ON c.host = :user AND ci.category = c.id")
  List<CategoryItemEntity> findMyItemList(@Param(value = "user") final Long user);
}
