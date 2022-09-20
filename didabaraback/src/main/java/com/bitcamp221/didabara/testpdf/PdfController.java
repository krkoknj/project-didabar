package com.bitcamp221.didabara.testpdf;

import com.bitcamp221.didabara.model.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class PdfController {
  @Autowired
  private PdfService pdfService;

  @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
          produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<?> test() {
//        List<UserInfoEntity> all = pdfService.findAll();
    UserInfoEntity one = pdfService.findOne();

    ByteArrayInputStream bis = GeneratePdfReport.userInfosReport(one);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=userInfosReport.pdf");


    return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
  }

}
