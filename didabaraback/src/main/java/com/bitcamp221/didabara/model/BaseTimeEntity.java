package com.bitcamp221.didabara.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
//JPA Entity들이 해당 클래스를 상속할때에 해당 클래스 내의 필드들도 컬럼으로 인식하도록 하는 어노테이션이다.
@MappedSuperclass
//엔티티가 삽입, 삭제, 조회, 등의 작업을 할때에 이벤트 처리를 하기 위한 어노테이션이다.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  @Column(name = "created_date", nullable = false)
  private LocalDate createdDate;

  @LastModifiedDate
  @Column(name = "modified_date", nullable = false)
  private LocalDate modifiedDate;
}