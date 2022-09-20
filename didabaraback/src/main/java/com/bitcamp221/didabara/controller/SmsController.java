package com.bitcamp221.didabara.controller;

import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SmsController {

  private final SmsService smsService;

  private final UserRepository userRepository;

  /**
   * 메서드 기능 : 인증번호로 아이디 찾기
   * 유저 핸드폰 번호에 인증코드 전송(이름 체크해서 핸드폰 번호와 같아야 전송)
   *
   * @param userPhoneNum // 유저 핸드폰 번호
   * @param realName     // 유저 이름
   * @return Map
   * {
   * code : code
   * username : username
   * }
   */
  @PostMapping("/findusername")
  public ResponseEntity<?> checkUserSms(@RequestParam("userPhoneNum") String userPhoneNum, @RequestParam("realName") String realName) {
    if (userPhoneNum == null || realName == null) {
      return ResponseEntity.badRequest().body("입력해주세요");
    }

    UserEntity byPhoneNumber = smsService.userPhoneAndName(userPhoneNum, realName);


    if (byPhoneNumber.getPhoneNumber().equals(userPhoneNum) && byPhoneNumber.getRealName().equals(realName)) {
      System.out.println("userPhoneNum = " + userPhoneNum);

      String[] strings = smsService.certifiedPhoneNumber(userPhoneNum);

      Map<String, String> usernameAndCode = new HashMap<>();
      usernameAndCode.put("code", strings[0]);
      usernameAndCode.put("username", strings[1]);

      return ResponseEntity.ok().body(usernameAndCode);
    } else {
      return ResponseEntity.badRequest().body("찾는 사용자가 없습니다.");
    }


  }

  /**
   * 메서드 기능 : 임시 비밀번호 발급
   *
   * @param userPhoneNum 유저 핸드폰 번호
   * @param realName     유저 이름
   * @param username     유저 이메일
   * @return Map
   * {
   * code : 인증 코드
   * username : 유저 이메일
   * tempPassword : 임시 비밀번호
   * }
   */
  @PostMapping("/findpassword")
  public ResponseEntity<?> checkUserSms(@RequestParam(value = "userPhoneNum", required = false) String userPhoneNum,
                                        @RequestParam(value = "realName", required = false) String realName,
                                        @RequestParam(value = "username", required = false) String username) {


    UserEntity byPhoneNumber = smsService.userPhoneAndRealname(userPhoneNum, realName, username);

    if (byPhoneNumber.getPhoneNumber().equals(userPhoneNum) && byPhoneNumber.getRealName().equals(realName) && byPhoneNumber.getUsername().equals(username)) {
      System.out.println("userPhoneNum = " + userPhoneNum);

      String[] strings = smsService.certifiedPhoneNumberAndTempCode(userPhoneNum);

      Map<String, String> usernameAndCode = new HashMap<>();
      usernameAndCode.put("code", strings[0]);
      usernameAndCode.put("username", strings[1]);
      usernameAndCode.put("tempPassword", strings[2]);

      return ResponseEntity.ok().body(usernameAndCode);
    } else {
      return ResponseEntity.badRequest().body("찾는 사용자가 없습니다.");
    }

  }

}
