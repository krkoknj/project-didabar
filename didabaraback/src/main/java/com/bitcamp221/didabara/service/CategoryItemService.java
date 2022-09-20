package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.presistence.CategoryItemRepository;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CategoryItemService {

  @Autowired
  private CategoryItemRepository categoryItemRepository;

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : 받아온 데이터에 대해서 사전 검사
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  private void validate(final CategoryItemEntity categoryItemEntity, final String message) {
    if (categoryItemEntity == null) {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : 받아온 데이터에 대해서 사전 검사
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  private void validateId(final Long id, final String message) {
    if (id == null) {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : CategoryItem 생성
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public List<CategoryItemEntity> create(final CategoryItemEntity categoryItemEntity) {
    final String message = "categoryItemService create";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(categoryItemEntity, message);

      categoryItemRepository.save(categoryItemEntity);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findAllByCategory(categoryItemEntity.getCategory());
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message), e);

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : categoryItemList 출력
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public List<CategoryItemEntity> findList(final Long categoryItemId) {
    final String message = "categoryItemService findCategoryItemList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryItemId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findAllByCategory(categoryItemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : categoryItem 삭제
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public List<CategoryItemEntity> deleteById(final Long categoryItemId) {
    final String message = "categoryItemService deleteById";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryItemId, message);

      final Long categoryId = categoryItemRepository.findCategoryId(categoryItemId);

      categoryItemRepository.deleteById(categoryItemId);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findAllByCategory(categoryId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }


  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : categoryItem 수정
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public List<CategoryItemEntity> update(final CategoryItemEntity categoryItemEntity) {
    final String message = "categoryItemService update";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(categoryItemEntity, message);

      final Optional<CategoryItemEntity> original = categoryItemRepository.findById(categoryItemEntity.getId());

      original.ifPresent(entity -> {

        entity.changeEntity(categoryItemEntity);

        categoryItemRepository.save(entity);
      });

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findAllByCategory(categoryItemEntity.getCategory());
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public Long countByCategory(final Long categoryId) {
    final String message = "categoryItemService countById";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.countByCategory(categoryId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public String findUrl(final Long id) {
    final String message = "categoryItemService findUrl";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(id, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findUrl(id);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public List<CategoryItemEntity> findAllItem(final Long userId) {
    final String message = "CategoryController findAllItem";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      return categoryItemRepository.findAllItem(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public List<CategoryItemEntity> findMyItemList(final Long userId) {
    final String message = "categoryItemService findMyItemList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findMyItemList(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public Long findCategoryId(final Long categoryItemId) {
    final String message = "categoryItemService findCategoryId";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryItemId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemRepository.findCategoryId(categoryItemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }
}
