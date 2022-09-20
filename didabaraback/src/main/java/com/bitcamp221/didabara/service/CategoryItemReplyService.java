package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO;
import com.bitcamp221.didabara.mapper.CategoryItemReplyMapper;
import com.bitcamp221.didabara.model.CategoryItemReplyEntity;
import com.bitcamp221.didabara.presistence.CategoryItemReplyRepository;
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
public class CategoryItemReplyService {

  @Autowired
  private CategoryItemReplyRepository categoryItemReplyRepository;

  @Autowired
  private CategoryItemReplyMapper categoryItemReplyMapper;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 데이터에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validate(final CategoryItemReplyEntity itemReplyEntity, final String message) {
    if (itemReplyEntity == null) {
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
//  메소드 정보 : list 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<ItemReplyAndUserDataDTO> findList(final Long itemId) {
    final String message = "itemReplyService findList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(itemId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemReplyMapper.findAllReplyData(itemId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 내 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CategoryItemReplyEntity> findMyList(final Long userId) {
    final String message = "itemReplyService findMyList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemReplyRepository.findAllByWriter(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Reply 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<ItemReplyAndUserDataDTO> create(final CategoryItemReplyEntity itemReplyEntity) {
    final String message = "itemReplyService create";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(itemReplyEntity, message);


      log.info(LogMessage.infoComplete(message));

      categoryItemReplyRepository.save(itemReplyEntity);

      return categoryItemReplyMapper.findAllReplyData(itemReplyEntity.getCategoryItem());
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Reply 삭제
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public void deleteById(final Long itemReplyId) {
    final String message = "itemReplyService deleteById";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(itemReplyId, message);

      final Long categoryItemId = categoryItemReplyRepository.findCategoryItemId(itemReplyId);

      categoryItemReplyRepository.deleteById(itemReplyId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Reply 수정
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<ItemReplyAndUserDataDTO> update(final CategoryItemReplyEntity itemReplyEntity) {
    final String message = "itemReplyService update";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(itemReplyEntity, message);

      final Optional<CategoryItemReplyEntity> original = categoryItemReplyRepository.findById(itemReplyEntity.getId());

      original.ifPresent(entity -> {
        entity.changeEntity(itemReplyEntity);

        categoryItemReplyRepository.save(entity);
      });

      log.info(LogMessage.infoComplete(message));

      return categoryItemReplyMapper.findAllReplyData(itemReplyEntity.getCategoryItem());
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Reply Writer 찾기
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public Long findWriter(final Long itemReplyId) {
    final String message = "itemReplyService findWriter";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(itemReplyId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemReplyRepository.findWriter(itemReplyId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public Long findCategoryItemId(final Long itemReplyId) {
    final String message = "itemReplyService findCategoryId";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(itemReplyId, message);

      log.info(LogMessage.infoComplete(message));

      return categoryItemReplyRepository.findCategoryItemId(itemReplyId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }
}
