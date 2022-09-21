package com.bitcamp221.didabara.testpdf;


import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.WordProcessingConvertOptions;
import com.groupdocs.conversion.options.load.PdfLoadOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController3 {

  private final ResourceLoader resourceLoader;

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

  @GetMapping("/{fileName}")
  public ResponseEntity<Resource> resourceFileDownload(@PathVariable String fileName) {
    try {
      Resource resource = resourceLoader
              .getResource("file:\\C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\" + fileName);
      File file = resource.getFile();

      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
              .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
              .body(resource);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(null);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
