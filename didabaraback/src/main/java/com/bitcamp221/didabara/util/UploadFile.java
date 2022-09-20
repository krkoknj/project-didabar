package com.bitcamp221.didabara.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bitcamp221.didabara.testpdf.S3UploadTest;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.FileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;
import com.groupdocs.conversion.options.load.WordProcessingLoadOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource(value = "application.properties")
public class UploadFile {

  private final AmazonS3Client s3Client;
  private final S3UploadTest s3UploadTest;
  private final String dirName = "myfile";
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String uploadCategoryImage(final MultipartFile file) {
    final String message = "UploadFile uploadCategoryImage";

    try {
      if (file.getSize() != 0) {
        log.info(LogMessage.infoJoin(message));

        final File uploadFile = convert(file);

        log.info("업로드 파일 : " + uploadFile);

        final String extension = uploadFile.getName().substring(uploadFile.getName().lastIndexOf("."));

        log.info("익스텐션 : " + extension);

        final File refactoring = new File(System.getProperty("user.dir") + "/" +
                UUID.randomUUID() + extension);

        log.info("리펙토링 : " + refactoring);

        XFile xFile1 = new XFile(uploadFile.getName());

        xFile1.renameTo(refactoring);

        log.info("리펙토링 이름 : " + refactoring.getName());

        final String uploadFileURI = putS3(refactoring, dirName + "/" + refactoring.getName());

        log.info("파일 URI : " + uploadFileURI);

        removeNewFile(refactoring);

        log.info(LogMessage.infoComplete(message));

        return uploadFileURI;
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public List<String> uploadCategoryItem(final MultipartFile file) throws IOException {
    final String message = "UploadFile uploadCategoryItem";

//    try {
    log.info(LogMessage.infoJoin(message));

    if (file.getSize() != 0) {
      WordProcessingLoadOptions loadOptions = new WordProcessingLoadOptions();

      File uploadFile = convert(file);

      System.out.println("uploadFile.toString() = " + uploadFile.toString());

      Converter converter = new Converter(uploadFile.toString(), loadOptions);

      PdfConvertOptions options = new PdfConvertOptions();

      String code = UUID.randomUUID().toString();

      String pathAndPdf = "C:\\Users\\nj\\Downloads\\" + code + ".pdf";

//        XFile file1 = new XFile(uploadFile.getName());

//        file1.renameTo(new File(pathAndPdf));

      converter.convert(pathAndPdf, options);

      File pdfFile = new File(pathAndPdf);

      Converter converter1 = new Converter(pathAndPdf.toString());

      ConvertOptions convertOptions = new FileType().fromExtension("jpg").getConvertOptions();

      String pathAndJpg = "C:\\Users\\nj\\Downloads\\" + code + ".jpg";

//        file1.renameTo(new File(pathAndJpg));

      converter.convert(pathAndJpg, convertOptions);
      log.info("도착");

      File jpgFile = new File(pathAndJpg);

      List<String> list = new ArrayList<>();

      list.add(s3UploadTest.upload(pdfFile, "myfile"));
      list.add(s3UploadTest.upload(jpgFile, "myfile"));

      log.info(file.getOriginalFilename());
      log.info(file.getName());

      removeNewFile(uploadFile);

      return list;
    } else {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
//    } catch (Exception e) {
//      log.error(LogMessage.errorJoin(message));
//
//      throw new RuntimeException(LogMessage.errorJoin(message));
//    }
  }

  private String putS3(final File uploadFile, final String fileName) {
    final String message = "UploadFile putS3";

    log.info("업로드 파일 : {}", uploadFile);
    try {
      if (uploadFile != null && fileName != null) {
        log.info(LogMessage.infoJoin(message));

        s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        log.info(LogMessage.infoComplete(message));

        return s3Client.getUrl(bucket, fileName).toString();
      } else {
        log.error(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  private File convert(final MultipartFile file) throws IOException {
    final String message = "UploadFile convert";

//    try {
    log.info(LogMessage.infoJoin(message));

    if (file.getSize() != 0) {

      final File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());

      log.info("컨버트 파일 : " + convertFile);

      if (convertFile.createNewFile()) {
        try {
          final FileOutputStream fos = new FileOutputStream(convertFile);

          fos.write(file.getBytes());

          return convertFile;
        } catch (Exception e) {
          log.warn(LogMessage.errorNull(message));

          throw new RuntimeException(LogMessage.errorNull(message));
        }
      } else {
        log.warn(LogMessage.errorNull(message));

        throw new RuntimeException(LogMessage.errorNull(message));
      }
    } else {
      log.error(LogMessage.errorNull(message));

      throw new RuntimeException(LogMessage.errorNull(message));
    }
//    } catch (Exception e) {
//      log.error(LogMessage.errorJoin(message));
//
//      throw new RuntimeException(LogMessage.errorJoin(message));
//    }
  }

  public void removeNewFile(final File file) {
    final String message = "UploadFile removeNewFile";

    try {
      if (file.delete()) {
        log.info(LogMessage.infoComplete(message));
      } else {
        log.warn(LogMessage.errorExist(message));
      }
    } catch (Exception e) {
      log.error(LogMessage.errorJoin(message));

      throw new RuntimeException(LogMessage.errorJoin(message));
    }
  }

  public void deleteFile(final String filePath) {
    DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, "myfile/" + filePath);

    s3Client.deleteObject(deleteObjectRequest);
  }

  //  public String fileTypeChangeUpload (final )
}
