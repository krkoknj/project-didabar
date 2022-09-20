package com.bitcamp221.didabara.testpdf;


import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;
import com.groupdocs.conversion.options.load.PdfLoadOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController3 {
  @PostMapping("/test33")
  public void test2() throws IOException {
//     C:\Users\nj\Downloads\Conholdate.Total_For_Java\GroupDocs.Total_for_Java\GroupDocs.Conversion_21.7-Java\GroupDocs.Conversion_21.7-Java\lib\groupdocs-conversion-21.7.jar
    String convertedFile = "C:\\Users\\nj\\Downloads\\white.pdf";
    PdfLoadOptions loadOptions = new PdfLoadOptions();
    loadOptions.setFlattenAllFields(true);
    String pdfFile = "C:\\Users\\nj\\Downloads\\2022.docx";
    Converter converter = new Converter(pdfFile, loadOptions);
    WordProcessingConvertOptions options = new WordProcessingConvertOptions();
    converter.convert(convertedFile, options);

    System.out.print("\nPdf document converted successfully. \nCheck output in " + convertedFile);

  }

}
