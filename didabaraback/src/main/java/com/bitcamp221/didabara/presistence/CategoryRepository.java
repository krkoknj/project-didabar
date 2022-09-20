package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  List<CategoryEntity> findAllByHost(@Param("host") final Long host);

  CategoryEntity findByInviteCode(@Param("inviteCode") final String inviteCode);

  @Query("SELECT c.host FROM CategoryEntity c WHERE c.id = :categoryId")
  Long findHost(@Param("categoryId") final Long categoryId);

  @Query("SELECT c.host FROM CategoryEntity c INNER JOIN CategoryItemEntity ci ON c.id = ci.category WHERE ci.id = :itemId")
  Long findCategoryHost(@Param("itemId") final Long itemId);

  @Query("SELECT c.id FROM CategoryEntity c WHERE c.inviteCode = :inviteCode")
  Long findCategory(@Param("inviteCode") final String inviteCode);

  @Query("SELECT c.profileImageUrl FROM CategoryEntity c WHERE c.id = :id")
  String findUrl(@Param("id") final Long id);

  boolean existsByIdAndHost(@Param("id") final Long id, @Param("host") final Long host);
}
