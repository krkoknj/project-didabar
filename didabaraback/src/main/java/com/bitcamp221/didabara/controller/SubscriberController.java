package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryDTO;
import com.bitcamp221.didabara.dto.CheckUserDTO;
import com.bitcamp221.didabara.dto.FindMyJoinListDTO;
import com.bitcamp221.didabara.dto.SubscriberDTO;
import com.bitcamp221.didabara.service.CategoryService;
import com.bitcamp221.didabara.service.SubscriberService;
import com.bitcamp221.didabara.util.ChangeType;
import com.bitcamp221.didabara.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

  @Autowired
  private SubscriberService subscriberService;

  @Autowired
  private CategoryService categoryService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(inviteCode)
//  -----------------------------------------------------
  @PostMapping("/create")
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                  @RequestBody final CategoryDTO categoryDTO) {
    final String message = "subscriber create";

    try {
      log.info(LogMessage.infoJoin(message));

      final Long categoryId = categoryService.findCategoryId(categoryDTO.getInviteCode());

      if (userId != null && categoryId != null &&
              !subscriberService.existsByCategoryAndUser(categoryId, Long.valueOf(userId)) &&
              !categoryService.existsByUser(categoryId, Long.valueOf(userId))) {
        SubscriberDTO subscriberDTO = new SubscriberDTO();

        subscriberDTO.setCategory(categoryId);
        subscriberDTO.setUser(Long.valueOf(userId));

        subscriberService.create(SubscriberDTO.toEntity(subscriberDTO));

        List<FindMyJoinListDTO> list = subscriberService.findMyJoinList(Long.valueOf(userId));

        return ResponseEntity.ok().body(list);
      } else {
        if (userId == null || categoryDTO.getInviteCode() == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.warn(LogMessage.errorMismatch(message));

          List<FindMyJoinListDTO> list = subscriberService.findMyJoinList(Long.valueOf(userId));

          return ResponseEntity.ok().body(list);
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @DeleteMapping("/delete/{categoryId}")
  public ResponseEntity<?> delete(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryId", required = false) final Long categoryId) {
    final String message = "subscriber delete";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryId != null) {
        subscriberService.deleteByCategoryAndUser(categoryId, Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        List<FindMyJoinListDTO> list = subscriberService.findMyJoinList(Long.valueOf(userId));

        return ResponseEntity.ok().body(list);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : category(id)
//  -----------------------------------------------------
  @GetMapping("/list/{categoryId}")
  public ResponseEntity<?> list(@AuthenticationPrincipal final String userId,
                                @PathVariable(value = "categoryId", required = false) final Long categoryId) {
    final String message = "subscriber list";

    try {
      log.info(LogMessage.errorJoin(message));

      if (userId != null && categoryId != null) {

        List<CheckUserDTO> list = subscriberService.findList(categoryId, Long.valueOf(userId));

        return ResponseEntity.ok().body(list);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Subscriber join list 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/myJoinList")
  public ResponseEntity<?> findMyJoinList(@AuthenticationPrincipal final String userId) {
    final String message = "subscriberController findMyJoinList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        log.info(LogMessage.infoComplete(message));

        List<FindMyJoinListDTO> list = subscriberService.findMyJoinList(Long.valueOf(userId));

        return ResponseEntity.ok().body(list);
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}
