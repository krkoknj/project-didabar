package com.bitcamp221.didabara.testpdf;

import com.bitcamp221.didabara.model.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends CrudRepository<UserInfoEntity, Long> {
}
