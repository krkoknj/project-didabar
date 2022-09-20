package com.bitcamp221.didabara.testpdf;

import com.bitcamp221.didabara.model.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PdfService {

  @Autowired
  private PdfRepository pdfRepository;

  public List<UserInfoEntity> findAll() {
    List<UserInfoEntity> userInfoEntityList = (List<UserInfoEntity>) pdfRepository.findAll();

    return userInfoEntityList;
  }

  public UserInfoEntity findOne() {
    Optional<UserInfoEntity> byId = pdfRepository.findById(1L);

    return byId.get();
  }
}
