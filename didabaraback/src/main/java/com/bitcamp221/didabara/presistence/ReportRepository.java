package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

  List<ReportEntity> findAllByWriter(@Param("writer") final Long writer);

  @Query("SELECT r.writer FROM ReportEntity r WHERE r.id = :reportId")
  Long findWriter(@Param("reportId") final Long reportId);

  boolean existsByCategoryAndWriter(@Param("category") final Long category, @Param("writer") final Long writer);
}
