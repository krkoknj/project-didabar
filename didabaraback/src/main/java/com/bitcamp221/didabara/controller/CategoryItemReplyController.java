package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CategoryItemReplyDTO;
import com.bitcamp221.didabara.dto.ItemReplyAndUserDataDTO;
import com.bitcamp221.didabara.model.CategoryItemReplyEntity;
import com.bitcamp221.didabara.service.CategoryItemReplyService;
import com.bitcamp221.didabara.service.CategoryItemService;
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
@RequestMapping("/categoryItemReply")
public class CategoryItemReplyController {

  @Autowired
  private CategoryItemReplyService itemReplyService;

  @Autowired
  private SubscriberService subscriberService;

  @Autowired
  private CategoryItemService categoryItemService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : itemReplyList 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @GetMapping("/list/{categoryItemId}")
  public ResponseEntity<?> findList(@AuthenticationPrincipal final String userId,
                                    @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "itemReply itemReplyList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (categoryItemId != null && userId != null) {
        final List<ItemReplyAndUserDataDTO> list = itemReplyService.findList(categoryItemId);

        log.info(LogMessage.infoComplete(message));

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
//  메소드 정보 : itemReplyMyList 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/myList")
  public ResponseEntity<?> findMyList(@AuthenticationPrincipal final String userId) {
    final String message = "itemReply myList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        final List<CategoryItemReplyEntity> replyEntities = itemReplyService.findMyList(Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toItemReplyDTO(replyEntities);
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
//  메소드 정보 : itemReply 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : itemReply(content), categoryItem(id)
//  -----------------------------------------------------
  @PostMapping("/create/page/{categoryItemId}")
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId,
                                  @RequestBody(required = false) final CategoryItemReplyDTO categoryItemReplyDTO) {
    final String message = "itemReply create";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryItemReplyDTO.getContent() != null && categoryItemId != null) {
        final CategoryItemReplyDTO itemReplyDTO = new CategoryItemReplyDTO(Long.valueOf(userId), categoryItemId, categoryItemReplyDTO.getContent());

        final List<ItemReplyAndUserDataDTO> list = itemReplyService.create(CategoryItemReplyDTO.toEntity(itemReplyDTO));

        log.info(LogMessage.infoComplete(message));

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
//  메소드 정보 : reply 수정
//  마지막 수정자 : 문병훈
//  필요 데이터 : itemReply(id, content)
//  -----------------------------------------------------
  @PutMapping("/update/page/{categoryItemId}")
  public ResponseEntity<?> update(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId,
                                  @RequestBody(required = false) final CategoryItemReplyDTO itemReplyDTO) {
    final String message = "itemReply update";

    try {
      log.info(LogMessage.infoJoin(message));

      final Long writer = itemReplyService.findWriter(itemReplyDTO.getId());

      final Long category = itemReplyService.findCategoryItemId(itemReplyDTO.getId());

      if (userId != null && Long.valueOf(userId) == writer && categoryItemId == category) {
        itemReplyDTO.setCategoryItem(categoryItemId);
        itemReplyDTO.setWriter(Long.valueOf(userId));

        final List<ItemReplyAndUserDataDTO> list = itemReplyService.update(CategoryItemReplyDTO.toEntity(itemReplyDTO));

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(list);
      } else {
        if (userId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorMismatch(message));

          throw new RuntimeException(LogMessage.errorMismatch(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : reply 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : itemReply(id)
//  -----------------------------------------------------
  @DeleteMapping("/delete/{itemReplyId}")
  public ResponseEntity<?> delete(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "itemReplyId", required = false) final Long itemReplyId) {
    final String message = "itemReply delete";

    try {
      log.info(LogMessage.infoJoin(message));

      log.info("데이터 " + itemReplyService.findWriter(itemReplyId));

      if (userId != null && Long.valueOf(userId) == itemReplyService.findWriter(itemReplyId)) {

        final Long categoryItemId = itemReplyService.findCategoryItemId(itemReplyId);

        itemReplyService.deleteById(itemReplyId);

        final List<ItemReplyAndUserDataDTO> list = itemReplyService.findList(categoryItemId);

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(list);
      } else {
        if (userId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorMismatch(message));

          throw new RuntimeException(LogMessage.errorMismatch(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}