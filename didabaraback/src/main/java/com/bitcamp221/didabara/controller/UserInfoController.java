package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.dto.S3Upload;
import com.bitcamp221.didabara.dto.UserAndUserInfoDTO;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/userinfo")
public class UserInfoController {

  private final UserInfoService userInfoService;
  private final S3Upload s3Upload;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/changepassword")
  public ResponseEntity<String> getMyPassword(@AuthenticationPrincipal String id, @RequestBody Map<String, String> map) {

    try {
      if (userInfoService.checkAndChange(id, map, passwordEncoder)) {
        return ResponseEntity.ok().body("업데이트 완료");
      }

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }


    return ResponseEntity.badRequest().body("업데이트 실패");
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
  public ResponseEntity<UserInfoEntity> adminBan(@AuthenticationPrincipal String id, @PathVariable String userId) {

    UserInfoEntity byIdInUser = userInfoService.amdinCheckAndBan(id, userId);


    return ResponseEntity.ok().body(byIdInUser);
  }


  @PostMapping("/upload")
  public ResponseEntity<UserInfoEntity> upload(@AuthenticationPrincipal String id, @RequestPart MultipartFile files) throws IOException {

    UserInfoEntity findUser = userInfoService.uploadRootFile(id, files);

    return ResponseEntity.ok().body(findUser);

  }

  /**
   * @param id token
   * @return UserInfoEntity, UserEntity
   */
  @GetMapping
  public ResponseEntity<Map<String, UserInfoEntity>> myPage(@AuthenticationPrincipal String id) {
    log.info("id={}", id);
    Long userid = Long.valueOf(id);

    // id로 내 정보 찾아오기 (user 테이블 user_info 테이블 조인)
    Map<String, UserInfoEntity> byIdMyPage = userInfoService.findByIdMyPage(userid);

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
   * @return 업데이트 유저
   */
  @PatchMapping
  public ResponseEntity<String> updateMyPage(@AuthenticationPrincipal String id, @RequestBody UserAndUserInfoDTO uid) {

    int checkRow = userInfoService.updateMyPage(id, uid);

    if (checkRow < 2) {

//      return ResponseEntity.<String>badRequest().body("업데이트 실패");

      return ResponseEntity.badRequest().body("업데이트 실패");
    }


    return ResponseEntity.ok().body("업데이트 완료");
  }


  /**
   * 작성자 : 김남주
   * 메서드 기능 : 회원 탈퇴
   *
   * @param id token
   * @return String 삭제 실패, 완료
   */
  @DeleteMapping
  public ResponseEntity<String> deleteMyPage(@AuthenticationPrincipal String id) {

    try {
      if (userInfoService.delete(id) == 0) {
        return ResponseEntity.badRequest().body("삭제 실패");
      } else {
        return ResponseEntity.ok().body("삭제 완료");
      }
    } catch (Exception e) {
      String error = e.getMessage();
      log.error("deleteMyPage()={}", error);
      return ResponseEntity.badRequest().body(error);
    }

  }


  @PostMapping
  private ResponseEntity<String> uploadText(@RequestParam("images") MultipartFile files,
                                            @AuthenticationPrincipal String id) throws IOException {
    return ResponseEntity.ok().body(s3Upload.upload(files, "myfile", id));
  }

  /**
   * 메서드 기능 : svg 파일 이름으로 유저 정보 업데이트
   *
   * @param svgName svg 파일 이름
   * @param id      유저 토큰 아이디
   * @return UserInfoEntity 업데이트된 유저 정보
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