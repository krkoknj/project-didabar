package com.bitcamp221.didabara.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class ReportEntity extends BaseTimeEntity {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //  신고자
  @Column(name = "writer", nullable = false)
  private Long writer;

  //  신고 받는 대상
  @Column(name = "category_id", nullable = false)
  private Long category;

  //  신고 시에 상세 내용 받는 컬럼
  @Column(name = "content", nullable = false)
  private String content;

  //  신고 유형(음란물, 유해물 등등)
  @Column(name = "report_category", nullable = false, length = 30)
  private String reportCategory;
}
