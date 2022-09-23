/*
package com.bitcamp221.didabara.testpdf;

import com.bitcamp221.didabara.dto.S3Upload;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.FileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;
import com.groupdocs.conversion.options.load.WordProcessingLoadOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class TestController2 {

  @Autowired
  private S3Upload s3Upload;

  @GetMapping("/test/svg")
  public ResponseEntity testSvg() {

    String svg = "<svg viewBox=\"0 0 36 36\" fill=\"none\" role=\"img\" xmlns=\"http://www.w3.org/2000/svg\" width=\"128\" height=\"128\"><title>1</title><mask id=\"mask__beam\" maskUnits=\"userSpaceOnUse\" x=\"0\" y=\"0\" width=\"36\" height=\"36\"><rect width=\"36\" height=\"36\" rx=\"72\" fill=\"#FFFFFF\"></rect></mask><g mask=\"url(#mask__beam)\"><rect width=\"36\" height=\"36\" fill=\"#e394a7\"></rect><rect x=\"0\" y=\"0\" width=\"36\" height=\"36\" transform=\"translate(9 -5) rotate(219 18 18) scale(1)\" fill=\"#e6d8cb\" rx=\"6\"></rect><g transform=\"translate(4.5 -4) rotate(9 18 18)\"><path d=\"M15 19c2 1 4 1 6 0\" stroke=\"#000000\" fill=\"none\" stroke-linecap=\"round\"></path><rect x=\"10\" y=\"14\" width=\"1.5\" height=\"2\" rx=\"1\" stroke=\"none\" fill=\"#000000\"></rect><rect x=\"24\" y=\"14\" width=\"1.5\" height=\"2\" rx=\"1\" stroke=\"none\" fill=\"#000000\"></rect></g></g></svg>";

    // svg 파일 저장 경로
    String fileName = "C:\\Users\\nj\\Downloads\\svgString.svg";

    // svg 파일 생성 준비
    byte[] svgByte = svg.getBytes();


    try {
      // svg 파일 생성
      OutputStream svgOutput = new FileOutputStream(fileName);
      svgOutput.write(svgByte);
      svgOutput.close();

      // 단계-1:입력 SVG문서를 Transcoder입력으로 읽습니다.
      TranscoderInput input_svg_image = new TranscoderInput("file:" + fileName);

      // 스텝 2:OutputStream이미지를 정의하고 TranscoderOutput에 연결합니다.
      OutputStream png_ostream = new FileOutputStream("C:\\Users\\nj\\Downloads\\testSvg.png");
      TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);

      OutputStream jpg_ostream = new FileOutputStream("C:\\Users\\nj\\Downloads\\testSvg.jpg");
      TranscoderOutput output_jpg_image = new TranscoderOutput(jpg_ostream);

      // 스텝 3:P/CGT구축/암호화 및 필요한 경우 힌트를 정의합니다.
      PNGTranscoder converter_png = new PNGTranscoder();

      JPEGTranscoder converter_jpg = new JPEGTranscoder();
      converter_jpg.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 0.8f);

      // 스텝 4:출력 및 쓰기 출력
      converter_png.transcode(input_svg_image, output_png_image);

      converter_jpg.transcode(input_svg_image, output_jpg_image);

      // 5단계- 닫힘/플러시 출력 스트림
      png_ostream.flush();
      png_ostream.close();

      jpg_ostream.flush();
      jpg_ostream.close();

      return ResponseEntity.ok().body(converter_jpg + ", " + converter_png);
    } catch (IOException | TranscoderException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body("에러");
    }
  }

  @PostMapping("/upload/test")
  public ResponseEntity test2(MultipartFile file) throws IOException {
//     C:\Users\nj\Downloads\Conholdate.Total_For_Java\GroupDocs.Total_for_Java\GroupDocs.Conversion_21.7-Java\GroupDocs.Conversion_21.7-Java\lib\groupdocs-conversion-21.7.jar
    String userId = "1";
    WordProcessingLoadOptions loadOptions = new WordProcessingLoadOptions();

    // MultipartFile 을 로컬에 저장하여 File 객체로 만듬
    File uploadFile = new File("C:\\Users\\nj\\Downloads\\" + file.getOriginalFilename());

    try (FileOutputStream fos = new FileOutputStream(uploadFile)) {

      // 파일을 읽음
      fos.write(file.getBytes());
      // 컨버터에 File 객체의
      System.out.println("uploadFile.toString() = " + uploadFile.toString());
      Converter converter = new Converter(uploadFile.toString(), loadOptions);
      ConvertOptions convertOptions = new FileType().fromExtension("pdf").getConvertOptions();
      converter.convertPageByPage(fos, convertOptions);

      PdfConvertOptions options = new PdfConvertOptions();

      String code = UUID.randomUUID().toString().substring(0, 6);
      String pathAndPdf = "C:\\Users\\nj\\Downloads\\" + code + ".pdf";

      converter.convert(pathAndPdf, options);

      File pdfFile = new File(pathAndPdf);

      System.out.println("pdfFile = " + pdfFile.toString());


      Converter converter1 = new Converter(pathAndPdf.toString());
      ConvertOptions convertOptions1 = new FileType().fromExtension("jpg").getConvertOptions();
      String pathAndJpg = "C:\\Users\\nj\\Downloads\\" + code + ".jpg";

      converter.convert(pathAndJpg, convertOptions1);

      File jpgFile = new File(pathAndJpg);

      String s3UrlJpg = s3Upload.upload(jpgFile, "myfile", userId);
      String s3UrlPdf = s3Upload.upload(pdfFile, "myfile", userId);

      return ResponseEntity.ok().body(s3UrlPdf + ", " + s3UrlJpg);
    }
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }
}
*/
