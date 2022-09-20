package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.dto.CheckUserDTO;
import com.bitcamp221.didabara.dto.FindMyJoinListDTO;
import com.bitcamp221.didabara.mapper.CategoryItemReplyMapper;
import com.bitcamp221.didabara.model.SubscriberEntity;
import com.bitcamp221.didabara.presistence.SubscriberRepository;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SubscriberService {

  @Autowired
  private SubscriberRepository subscriberRepository;

  @Autowired
  private CategoryItemReplyMapper categoryItemReplyMapper;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 데이터에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validate(final SubscriberEntity subscriberEntity, final String message) {
    if (subscriberEntity == null) {
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
//  메소드 정보 : Subscriber 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public void create(final SubscriberEntity subscriberEntity) {
    final String message = "subscriberService create";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(subscriberEntity, message);

      subscriberRepository.save(subscriberEntity);

      log.info(LogMessage.infoComplete(message));
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 삭제
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public void deleteByCategoryAndUser(final Long categoryId, final Long userId) {
    final String message = "subscriberService deleteByCategoryIdAndUserId";

    try {
      validateId(categoryId, message);
      validateId(userId, message);

      log.info(LogMessage.infoJoin(message));
      log.info("값 {}, {}", categoryId, userId);

      subscriberRepository.deleteByCategoryAndUser(categoryId, userId);

      log.info(LogMessage.infoComplete(message));
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<CheckUserDTO> findList(final Long categoryId, final Long userId) {
    final String message = "subscriberService findList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryId, message);
      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return subscriberRepository.findSubscriberList(categoryId, userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 해당 카테고리가 존재하는지 확인
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public boolean existsByCategoryAndUser(final Long categoryId, final Long userId) {
    final String message = "subscriberService existsByCategoryIdAndUserId";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(categoryId, message);
      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return subscriberRepository.existsByCategoryAndUser(categoryId, userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public List<FindMyJoinListDTO> findMyJoinList(final Long userId) {
    final String message = "subscriberService findMyJoinList";

//    try {
    log.info(LogMessage.infoJoin(message));

    validateId(userId, message);

    log.info(LogMessage.infoComplete(message));

    return subscriberRepository.findMyJoinList(userId);
//    } catch (Exception e) {
//      log.error(LogMessage.errorJoin(message));
//
//      throw new RuntimeException(LogMessage.errorJoin(message));
//    }
  }

//  public Boolean findByCategoryAndUser(final Long category, final Long user) {
//    final String message = "subscriberService findByCategoryAndUser";
//
//    try {
//      log.info(LogMessage.infoJoin(message));
//
//      validateId(category, message);
//      validateId(user, message);
//
//      log.info(LogMessage.infoComplete(message));
//
//      return subscriberRepository.existsByCategoryAndUser(category, user);
//    } catch (Exception e) {
//      log.error(LogMessage.errorJoin(message));
//
//      throw new RuntimeException(LogMessage.errorJoin(message));
//    }
//  }
}