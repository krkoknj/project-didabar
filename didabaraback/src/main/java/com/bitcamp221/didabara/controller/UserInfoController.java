package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.S3Upload;
import com.bitcamp221.didabara.dto.UserUserInfoDTO;
import com.bitcamp221.didabara.mapper.UserInfoMapper;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {

  @Autowired
  private UserInfoService userInfoService;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private UserInfoMapper userInfoMapper;

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private S3Upload s3Upload;

  @Autowired
  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/changepassword")
  public ResponseEntity<?> getMyPassword(@AuthenticationPrincipal String id, @RequestBody Map<String, String> map) {

    try {
      if (userInfoService.checkAndChange(id, map, passwordEncoder)) {
        return ResponseEntity.ok().body("업데이트 완료");
      }

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }


    return ResponseEntity.badRequest().body("업데이트 완료 안됌");
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

  /**
   * 작성자 : 김남주
   * 메서드 기능 : admin 권한일시 유저의 밴 관리
   * 유저의 ban 값이 true면 fasle로 false면 true
   * 마지막 작성자 : 김남주
   *
   * @param id     token user id
   * @param userId 밴을 관리할 유저의 아이디
   * @return UserInfoEntity
   */
  @GetMapping("/admin/{userId}")
  public ResponseEntity<?> adminBan(@AuthenticationPrincipal String id, @PathVariable String userId) {
    // 권한 체크
    UserInfoEntity admin = userInfoMapper.findByIdInUserInfo(id);
    if (admin.getRole() != 1) {
      return ResponseEntity.badRequest().body("관리자가 아닙니다.");
    }

    // 밴 체크
    UserInfoEntity byIdInUser = userInfoMapper.findByIdInUserInfo(userId);

    if (byIdInUser.isBan() == false) {
      byIdInUser.setBan(true);
      userInfoMapper.updateBan(byIdInUser);
    } else {
      byIdInUser.setBan(false);
      userInfoMapper.updateBan(byIdInUser);
    }

    return ResponseEntity.ok().body(byIdInUser);
  }


  @PostMapping("/upload")
  public UserInfoEntity upload(@AuthenticationPrincipal String id, @RequestPart MultipartFile files) throws IOException {
    // db에 저장할 파일 이름 생성
    String code = UUID.randomUUID().toString().substring(0, 6);

    // 매개변수로 들어온 파일의 이름 가져오기
    String sourceFileName = files.getOriginalFilename();
    // 파일의 확장자 가져오기
    String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

    File destinationFile;
    String destinationFileName;
    String fileUrl = "C:\\projectbit\\didabara\\didabaraback\\src\\main\\resources\\static\\imgs\\";
//    String fileUrl = "https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/";

    do {
      destinationFileName = code + "." + sourceFileNameExtension;
      destinationFile = new File(fileUrl + destinationFileName);
    } while (destinationFile.exists());

    destinationFile.getParentFile().mkdirs();
    files.transferTo(destinationFile);

    Long userid = Long.valueOf(id);


    UserInfoEntity findUser = userInfoRepository.findById(userid).orElseThrow(() ->
            new IllegalArgumentException("해당 아이디가 없습니다."));

    findUser.setFilename(destinationFileName);
    findUser.setProfileImageUrl(fileUrl);
    findUser.setFileOriName(sourceFileName);

    userInfoRepository.save(findUser);
    return findUser;

  }

  // 내 정보 보기
  @GetMapping
  public ResponseEntity<?> myPage(@AuthenticationPrincipal String id) {
    log.info("id={}", id);
    Long userid = Long.valueOf(id);

    // id로 내 정보 찾아오기 (user 테이블 user_info 테이블 조인)
    Map byIdMyPage = userInfoService.findByIdMyPage(userid);

    byIdMyPage.put("password", null);


    return ResponseEntity.ok().body(byIdMyPage);
  }

  /**
   * 작성자 : 김남주
   * 메서드 기능 : 마이페이지 수정 (값이 안들어올시에는 유저의 원래 입력값으로 업데이트)
   * 마지막 작성자 : 김남주
   *
   * @param id  JWT id
   * @param uid 컬럼명들
   * @return
   */
  @PatchMapping
  public ResponseEntity<?> updateMyPage(@AuthenticationPrincipal String id, @RequestBody UserUserInfoDTO uid) {

    String s = uid.toString();
    System.out.println("s = " + s);
    // s = nickname:dwkow job:null password:1111 username:adwddawd realName:dwdadw


    int checkRow = userInfoService.updateMyPage(id, uid);

    System.out.println("checkRow = " + checkRow);

    if (checkRow < 2) {
      return ResponseEntity.badRequest().body("업데이트 실패");
    }

    return ResponseEntity.ok().body(uid);
  }


  /**
   * 작성자 : 김남주
   * 메서드 기능 : 회원 탈퇴
   *
   * @param id
   * @return
   */
  @DeleteMapping
  public ResponseEntity<?> deleteMypage(@AuthenticationPrincipal String id) {

    try {
      int delete = userInfoService.delete(id);
      return ResponseEntity.ok().body(delete);
    } catch (Exception e) {
      String error = e.getMessage();
      return ResponseEntity.badRequest().body(error);
    }

  }


  @PostMapping
  private ResponseEntity<?> uploadText(@RequestParam("images") MultipartFile files,
                                       @AuthenticationPrincipal String id) throws IOException {
    return ResponseEntity.ok().body(s3Upload.upload(files, "myfile", id));
  }

  /**
   * 메서드 기능 : svg 파일 이름으로 유저 정보 업데이트
   *
   * @param svgName svg 파일 이름
   * @param id      유저 토큰 아이디
   * @return UserInfoEntity 업데이트된 유저 정보
   * @throws IOException
   */
  @PatchMapping("/svg")
  private ResponseEntity<?> uploadSvg(@RequestParam("svgname") String svgName, @AuthenticationPrincipal String id) {

    UserInfoEntity updatedUserInfoEntity = null;
    try {
      updatedUserInfoEntity = userInfoService.svgUpdate(svgName, id);
    } catch (Exception e) {
      String message = e.getMessage();
      log.error("uploadSvg()={}", message);
      return ResponseEntity.badRequest().body(message);
    }

    return ResponseEntity.ok().body(updatedUserInfoEntity);
  }


}