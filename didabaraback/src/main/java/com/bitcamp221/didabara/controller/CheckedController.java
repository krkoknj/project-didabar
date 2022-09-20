package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.CheckUserDTO;
import com.bitcamp221.didabara.dto.CheckedDTO;
import com.bitcamp221.didabara.model.CategoryItemEntity;
import com.bitcamp221.didabara.service.CategoryService;
import com.bitcamp221.didabara.service.CheckedService;
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
@RequestMapping("/checked")
public class CheckedController {

  @Autowired
  private CheckedService checkedService;

  @Autowired
  private CategoryService categoryService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @PostMapping("/create/item/{categoryItemId}")
  public ResponseEntity<?> create(@AuthenticationPrincipal final String userId,
                                  @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked create";

    try {
      log.info(LogMessage.infoJoin(message));

      final Long host = categoryService.findCategoryItemHost(categoryItemId);

      final Long check = checkedService.findCheck(categoryItemId, Long.valueOf(userId));

      if (check == null && userId != null && categoryItemId != null && host != Long.valueOf(userId)) {

        CheckedDTO checkedDTO = new CheckedDTO(Long.valueOf(userId), categoryItemId);

        checkedService.create(CheckedDTO.toEntity(checkedDTO));

        List<CheckUserDTO> userDTOS = checkedService.findCheckUserList(categoryItemId, Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(userDTOS);
      } else {
        if (userId == null || categoryItemId == null) {
          log.error(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        } else {
          log.error(LogMessage.errorExist(message));

          throw new RuntimeException(LogMessage.errorExist(message));
        }
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 삭제
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
//  @DeleteMapping("/delete")
//  public void delete(@AuthenticationPrincipal final String userId,
//                     @RequestParam(value = "categoryItemId", required = false) final Long categoryItemId) {
//    final String message = "checked delete";
//
//    try {
//      log.info(LogMessage.infoJoin(message));
//
//      if (userId != null && categoryItemId != null) {
//        checkedService.delete(categoryItemId);
//
//        log.info(LogMessage.infoComplete(message));
//      } else {
//        log.error(LogMessage.errorNull(message));
//
//        throw new RuntimeException(LogMessage.errorNull(message));
//      }
//    } catch (Exception e) {
//      log.error(LogMessage.errorJoin(message));
//
//      throw new RuntimeException(LogMessage.errorJoin(message));
//    }
//  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Check 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @GetMapping("/list/item/{categoryItemId}")
  public ResponseEntity<?> checkUserList(@AuthenticationPrincipal final String userId,
                                         @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked checkUserList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryItemId != null) {
        log.info(LogMessage.infoComplete(message));

        List<CheckUserDTO> list = checkedService.findCheckUserList(categoryItemId, Long.valueOf(userId));

        return ResponseEntity.ok().body(list);
      } else {
        log.error(LogMessage.errorJoin(message));

        throw new RuntimeException(LogMessage.errorJoin(message));
      }

    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : unCheck 리스트 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id)
//  -----------------------------------------------------
  @GetMapping("/list/item/{categoryItemId}/un-checkuser")
  public ResponseEntity<?> unCheckUserList(@AuthenticationPrincipal final String userId,
                                           @PathVariable(value = "categoryItemId", required = false) final Long categoryItemId) {
    final String message = "checked unCheckUserList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryItemId != null) {

        List<CheckUserDTO> list = checkedService.findUnCheckUserList(categoryItemId, Long.valueOf(userId));

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
//  메소드 정보 : 나의 Check 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/my-checklist")
  public ResponseEntity<?> MyCheckList(@AuthenticationPrincipal final String userId) {
    final String message = "checked MyCheckList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        List<CategoryItemEntity> list = checkedService.findMyCheckList(Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toCategoryItemDTO(list);
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
//  메소드 정보 : 나의 unCheck 리스트 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/my-un-checklist")
  public ResponseEntity<?> myUnCheckList(@AuthenticationPrincipal final String userId) {
    final String message = "checked unMyCheckList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        List<CategoryItemEntity> list = checkedService.findMyUnCheckList(Long.valueOf(userId));

        log.info(LogMessage.infoComplete(message));

        return ChangeType.toCategoryItemDTO(list);
      } else {
        log.info(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      return ChangeType.toException(e);
    }
  }
}
