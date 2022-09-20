package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.model.ReportEntity;
import com.bitcamp221.didabara.presistence.ReportRepository;
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
public class ReportService {

  @Autowired
  private ReportRepository reportRepository;

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 받아온 데이터에 대해서 사전 검사
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  private void validate(final ReportEntity reportEntity, final String message) {
    if (reportEntity == null) {
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
//  메소드 정보 : Report 생성
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public void create(final ReportEntity reportEntity) {
    final String message = "reportService create";

    try {
      log.info(LogMessage.infoJoin(message));

      validate(reportEntity, message);

      log.info(LogMessage.infoComplete(message));

      reportRepository.save(reportEntity);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Report 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public Optional<ReportEntity> findReport(final Long reportId) {
    final String message = "reportService findReport";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(reportId, message);

      log.info(LogMessage.infoComplete(message));

      return reportRepository.findById(reportId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : 특정 user의 Report List 출력
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<ReportEntity> findMyList(final Long userId) {
    final String message = "reportService findMyList";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(userId, message);

      log.info(LogMessage.infoComplete(message));

      return reportRepository.findAllByWriter(userId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  //  ---------------------------------------------------
//  작성자 : 문병훈
//  메소드 정보 : Report List 출력 (관리자 기능)
//  마지막 수정자 : 문병훈
//  -----------------------------------------------------
  public List<ReportEntity> findList() {
    final String message = "reportService findList";

    try {
      log.info(LogMessage.infoJoin(message));

      log.info(LogMessage.infoComplete(message));

      return reportRepository.findAll();
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public Long findWriter(final Long reportId) {
    final String message = "reportService findWriter";

    try {
      log.info(LogMessage.infoJoin(message));

      log.info(LogMessage.infoComplete(message));

      return reportRepository.findWriter(reportId);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public boolean existsByCategoryAndWriter(final Long category, final Long user) {
    final String message = "reportService existByCategoryAndWriter";

    try {
      log.info(LogMessage.infoJoin(message));

      validateId(category, message);
      validateId(user, message);

      log.info(LogMessage.infoComplete(message));

      return reportRepository.existsByCategoryAndWriter(category, user);
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }
}
