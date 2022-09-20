package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.ReportDTO;
import com.bitcamp221.didabara.model.ReportEntity;
import com.bitcamp221.didabara.service.ReportService;
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
@RequestMapping("/report")
public class ReportController {

  @Autowired
  private ReportService reportService;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Report 생성
//  마지막 수정자 : 문병훈
//  필요 데이터 : categoryItem(id), report(content, reportCategory)
//  -----------------------------------------------------
  @PostMapping("/create/{categoryId}")
  public void create(@AuthenticationPrincipal final String userId,
                     @PathVariable(value = "categoryId", required = false) final Long categoryId,
                     @RequestBody(required = false) final ReportDTO reportDTO) {
    final String message = "report create";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && categoryId != null && reportDTO != null &&
              !reportService.existsByCategoryAndWriter(categoryId, Long.valueOf(userId))) {
        reportDTO.setCategory(categoryId);
        reportDTO.setWriter(Long.valueOf(userId));

        reportService.create(ReportDTO.toEntity(reportDTO));

        log.info(LogMessage.infoComplete(message));
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
//  메소드 정보 : Report 출력
//  마지막 수정자 : 문병훈
//  필요 데이터 : report(id)
//  -----------------------------------------------------
  @GetMapping("/page/{reportId}")
  public ResponseEntity<?> findReport(@AuthenticationPrincipal final String userId,
                                      @PathVariable(value = "reportId", required = false) final Long reportId) {
    final String message = "report findReport";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null && Long.valueOf(userId) == reportService.findWriter(reportId)) {
        final ReportDTO reportDTO = new ReportDTO(reportService.findReport(reportId));

        log.info(LogMessage.infoComplete(message));

        return ResponseEntity.ok().body(reportDTO);
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
//  메소드 정보 : 특정 user의 Report List 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/mylist")
  public ResponseEntity<?> MyList(@AuthenticationPrincipal final String userId) {
    final String message = "report MyList";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        final List<ReportEntity> reportEntities = reportService.findMyList(Long.valueOf(userId));

        return ChangeType.toReportDTO(reportEntities);
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
//  메소드 정보 : Report List 출력 (관리자 기능)
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  @GetMapping("/list")
  public ResponseEntity<?> list(@AuthenticationPrincipal final String userId) {
    final String message = "report list";

    try {
      log.info(LogMessage.infoJoin(message));

      if (userId != null) {
        final List<ReportEntity> reportEntities = reportService.findList();

        return ChangeType.toReportDTO(reportEntities);
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