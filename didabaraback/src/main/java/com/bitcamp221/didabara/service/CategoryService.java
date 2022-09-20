package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.CategoryEntity;
import com.bitcamp221.didabara.presistence.CategoryRepository;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  //  -----------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : 받아온 데이터에 대해서 사전 검사
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  private void validate(final CategoryEntity categoryEntity, final String message) {
    if (categoryEntity == null) {
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

  private void validateString(final String st, final String message) {
    if (st == null) {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : Category 생성
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public Optional<CategoryEntity> create(final CategoryEntity categoryEntity) {
    final String message = "categoryService create";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(categoryEntity, message);

      categoryRepository.save(categoryEntity);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findById(categoryEntity.getId());
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : Category 정보 출력
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public Optional<CategoryEntity> findCategory(final Long categoryId) {
    final String message = "categoryService findByCategory";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryId, message);

      return categoryRepository.findById(categoryId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : user본인이 생성한 Category 전체 출력
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public List<CategoryEntity> findMyList(final Long userId) {
    final String message = "categoryService myList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findAllByHost(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : Category 수정
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public Optional<CategoryEntity> update(final CategoryEntity categoryEntity) {
    final String message = "categoryService update";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(categoryEntity, message);

      final Optional<CategoryEntity> original = categoryRepository.findById(categoryEntity.getId());

      original.ifPresent(entity -> {

        entity.changeEntity(categoryEntity);

        categoryRepository.save(entity);
      });

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findById(categoryEntity.getId());
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : Category 삭제
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public List<CategoryEntity> deleteById(final Long host, final Long categoryId) {
    final String message = "categoryService deleteById";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(host, message);
      validateId(categoryId, message);

      categoryRepository.deleteById(categoryId);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findAllByHost(host);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }
  //
  //  //  ---------------------------------------------------
  ////  작성자 : 문병훈
  ////  메소드 정보 : user가 만든 카테고리에 대한 존재 여부 확인
  ////  마지막 수정자 : 문병훈
  ////  -----------------------------------------------------
  //  public boolean isExistCategory(final Long categoryId) {
  //    final String message = "categoryService isExistCategory";
  //
  //    try {
  //      log.info(LogMessage.infoJoin(message));
  //
  //      validateId(categoryId, message);
  //
  //      log.info(LogMessage.infoComplete(message));
  //
  //      return categoryRepository.findById(categoryId).isEmpty();
  //    } catch (Exception e) {
  //      log.error(LogMessage.errorJoin(message));
  //
  //      throw new RuntimeException(LogMessage.errorJoin(message));
  //    }
  //  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : 특정 category에 대해 호스트 아이디 찾아냄
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public Long findHost(final Long categoryId) {
    final String message = "categoryService findHost";

    //    try {
    log.info(LogMessage.infoJoin(message));

    validateId(categoryId, message);

    log.info(LogMessage.infoComplete(message));

    return categoryRepository.findHost(categoryId);
    //    } catch (Exception e) {
    //      log.error(LogMessage.errorJoin(message));
    //
    //      throw new RuntimeException(LogMessage.errorJoin(message));
    //    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : category item id로 host 찾기
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public Long findCategoryItemHost(final Long itemId) {
    final String message = "categoryService findCategoryHost";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(itemId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findCategoryHost(itemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
  //  작성자 : 문병훈
  //  메소드 정보 : Category 일치 확인
  //  마지막 수정자 : 문병훈
  //  -----------------------------------------------------
  public Long findCategoryId(final String inviteCode) {
    final String message = "categoryService findCategoryId";

    try {
      log.info(LogMessage.infoJoin(message));

      validateString(inviteCode, message);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findCategory(inviteCode);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public String findUrl(final Long id) {
    final String message = "categoryService findUrl";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(id, message);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.findUrl(id);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public boolean existsByUser(final Long categoryId, final Long userId) {
    final String message = "CategoryService existsByUser";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryRepository.existsByIdAndHost(categoryId, userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }
}
