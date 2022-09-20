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
@Table(name = "checked")
public class CheckedEntity extends BaseTimeEntity {
//  해당 테이블 생성이 안된 유저는 문서를 읽지 않는 유저로 null반환.
//  left join해서 널일 경우 false로 인식하게 하면 됨.

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long user;

  @Column(name = "category_item_id", nullable = false)
  private Long categoryItem;
}
