package com.bitcamp221.didabara.dto;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.bitcamp221.didabara.mapper.UserInfoMapper;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource(value = "application.properties")
public class S3Upload {

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private UserInfoMapper userInfoMapper;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  private final AmazonS3Client s3Client;


  public String getS3(String bucket, String fileName) {

    return s3Client.getUrl(bucket, fileName).toString();
  }

  public String upload(MultipartFile file, String dirName, String id) throws IOException {
    File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("file 전달에 실패했습니다."));
    String extension = uploadFile.getName().substring(uploadFile.getName().lastIndexOf("."));
    System.out.println("extension = " + extension);

       /* if (extension.equals("docx")){
            // ---
            InputStream docxInputStream = new FileInputStream(docxFileName);
            OutputStream pdfOutputStream = new FileOutputStream(pdfFileName);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.MS_WORD)
                    .to(pdfOutputStream).as(DocumentType.PDF)
                    .execute();
            converter.shutDown();
            // ---
        }*/


    File news = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID() + extension);
    uploadFile.renameTo(news);
    return upload(news, dirName, id);
  }

  public String upload(File uploadFile, String dirName, String id) {
    String fileName = dirName + "/" + uploadFile.getName();
    String uploadImageURI = putS3(uploadFile, fileName);
    String dBPathName = uploadImageURI.substring(0, 56);
    String extensionName = uploadImageURI.substring(uploadImageURI.lastIndexOf("/") + 1);
    String dbFilename = uploadImageURI.substring(uploadImageURI.lastIndexOf("/") + 1);

    Optional<UserInfoEntity> byId = userInfoRepository.findById(Long.valueOf(id));
    byId.get().setProfileImageUrl(dBPathName);
    byId.get().setFilename(dbFilename);
    userInfoRepository.save(byId.get());

    removeNewFile(uploadFile);
    return uploadImageURI;
  }

  private String putS3(File uploadFile, String fileName) {
    s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
    return s3Client.getUrl(bucket, fileName).toString();
  }

  public int deleteFile(String fileName, String id) {
    Optional<UserInfoEntity> byId = userInfoRepository.findById(Long.valueOf(id));
    String filename = byId.get().getFilename();
    System.out.println("filename = " + filename);
    if (fileName.equals(filename)) {
      userInfoMapper.updateEmpty(id);
    }
    DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, "myfile/" + fileName);
    s3Client.deleteObject(deleteObjectRequest);
    return 1;
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("파일 삭제 완료");
    } else {
      log.info("파일 삭제 실패");
    }
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
    System.out.println("convertFile = " + convertFile);
    System.out.println("convertFile.getName() = " + convertFile.getName()); //download.jpg
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }

  public boolean download(String fileKey, String downloadFileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (fileKey == null) {
      return false;
    }
    S3Object fullObject = null;
    try {
      fullObject = s3Client.getObject("didabara", fileKey); //fileKey = myfile/b11a0412-4027-469e-a02f-98f646e36c6e.docx
      if (fullObject == null) {
        return false;
      }
    } catch (AmazonS3Exception e) {
      throw new Exception("다운로드 파일이 존재하지 않습니다.");
    }

    OutputStream os = null;
    FileInputStream fis = null;
    boolean success = false;
    try {
      S3ObjectInputStream objectInputStream = fullObject.getObjectContent();
      byte[] bytes = IOUtils.toByteArray(objectInputStream);

      String fileName = null;
      if (downloadFileName != null) {
        //fileName= URLEncoder.encode(downloadFileName, "UTF-8").replaceAll("\\+", "%20");
        fileName = getEncodedFilename(request, downloadFileName);
      } else {
        fileName = getEncodedFilename(request, fileKey); // URLEncoder.encode(fileKey, "UTF-8").replaceAll("\\+", "%20");
      }

      response.setContentType("application/octet-stream;charset=UTF-8");
      response.setHeader("Content-Transfer-Encoding", "binary");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
      response.setHeader("Content-Length", String.valueOf(fullObject.getObjectMetadata().getContentLength()));
      response.setHeader("Set-Cookie", "fileDownload=true; path=/");
      FileCopyUtils.copy(bytes, response.getOutputStream());
      success = true;
    } catch (IOException e) {
      log.debug(e.getMessage(), e);
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
      } catch (IOException e) {
        log.debug(e.getMessage(), e);
      }
      try {
        if (os != null) {
          os.close();
        }
      } catch (IOException e) {
        log.debug(e.getMessage(), e);
      }
    }
    return success;
  }

  private String getEncodedFilename(HttpServletRequest request, String displayFileName) throws UnsupportedEncodingException {
    String header = request.getHeader("User-Agent");

    String encodedFilename = null;
    if (header.indexOf("MSIE") > -1) {
      encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
    } else if (header.indexOf("Trident") > -1) {
      encodedFilename = URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
    } else if (header.indexOf("Chrome") > -1) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < displayFileName.length(); i++) {
        char c = displayFileName.charAt(i);
        if (c > '~') {
          sb.append(URLEncoder.encode("" + c, "UTF-8"));
        } else {
          sb.append(c);
        }
      }
      encodedFilename = sb.toString();
    } else if (header.indexOf("Opera") > -1) {
      encodedFilename = "\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"";
    } else if (header.indexOf("Safari") > -1) {
      encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"", "UTF-8");
    } else {
      encodedFilename = URLDecoder.decode("\"" + new String(displayFileName.getBytes("UTF-8"), "8859_1") + "\"", "UTF-8");
    }
    return encodedFilename;

  }
}