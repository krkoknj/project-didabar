package com.bitcamp221.didabara.dto;

import com.bitcamp221.didabara.model.ReportEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

  private Long id;
  private Long writer;
  private Long category;
  private String content;
  private String reportCategory;
  private LocalDate createdDate;
  private LocalDate modifiedDate;

  public ReportDTO(ReportEntity reportEntity) {
    this.id = reportEntity.getId();
    this.writer = reportEntity.getWriter();
    this.category = reportEntity.getCategory();
    this.content = reportEntity.getContent();
    this.reportCategory = reportEntity.getReportCategory();
    this.createdDate = reportEntity.getCreatedDate();
    this.modifiedDate = reportEntity.getModifiedDate();
  }

  public ReportDTO(Optional<ReportEntity> report) {
    this.id = report.get().getId();
    this.writer = report.get().getWriter();
    this.category = report.get().getCategory();
    this.content = report.get().getContent();
    this.reportCategory = report.get().getReportCategory();
    this.createdDate = report.get().getCreatedDate();
    this.modifiedDate = report.get().getModifiedDate();
  }

  public static ReportEntity toEntity(final ReportDTO reportDTO) {

    return ReportEntity.builder()
            .id(reportDTO.getId())
            .writer(reportDTO.getWriter())
            .category(reportDTO.getCategory())
            .content(reportDTO.getContent())
            .reportCategory(reportDTO.getReportCategory())
            .build();
  }
}
