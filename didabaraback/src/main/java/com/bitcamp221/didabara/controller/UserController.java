package com.bitcamp221.didabara.controller;


import com.bitcamp221.didabara.dto.ResponseDTO;
import com.bitcamp221.didabara.dto.UserDTO;
import com.bitcamp221.didabara.model.EmailConfigEntity;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.EmailConfigRepository;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.security.TokenProvider;
import com.bitcamp221.didabara.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserInfoRepository userInfoRepository;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private EmailConfigRepository emailConfigRepository;

//  @Autowired
//  NotificationService notificationService;

  //  회원가입
//  http://localhost:8080/auth/signup
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
//      받은 데이터 유효성 검사
      if (userDTO == null || userDTO.getPassword() == null) {
        throw new RuntimeException("Invalid Password value");
      }
//
//      요청을 이용해 저장할 유저 객체 생성
      UserEntity userEntity = UserEntity.builder()
              .username(userDTO.getUsername())
              .password(passwordEncoder.encode(userDTO.getPassword()))
              .nickname(userDTO.getNickname())
              .realName(userDTO.getRealName())
              .phoneNumber(userDTO.getPhoneNumber())
              .build();

//      서비스를 이용해 리포지터리에 유저 저장
      UserEntity registeredUser = userService.creat(userEntity);


      // 회원가입한 id값 가져가서 user_info 테이블 생성
      UserInfoEntity userInfoEntity = UserInfoEntity.builder()
              .id(registeredUser.getId())
              .fileOriName("default.jpg")
              .profileImageUrl("https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/")
              .filename("def54545-1d55-43b5-9f69-eb15c7ebe43f.jpg")
              .job("")
              .build();

      userInfoRepository.save(userInfoEntity);


      //응답객체 만들기(패스워드 제외)
      UserDTO responseUserDTO = UserDTO.builder()
              .id(registeredUser.getId())
              .username(registeredUser.getUsername())
              .nickname(registeredUser.getNickname())
              .build();

      log.info("회원가입 완료");


      return ResponseEntity.ok().body(responseUserDTO);

    } catch (Exception e) {
      ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

      return ResponseEntity.badRequest().body(responseDTO);
    }

  }

  //  로그인
  @PostMapping(value = "/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
    UserEntity user;
    try {
      user = userService.getByCredentials(
              userDTO.getUsername(),
              userDTO.getPassword(),
              passwordEncoder
      );

      if (user == null) {
        throw new Exception("찾은 사용자가 없습니다.");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

    Optional<EmailConfigEntity> byId = emailConfigRepository.findById(user.getId());

    if (byId.isPresent()) {
      if (byId.get().getCheck() == false) {
        return ResponseEntity.badRequest().body("인증 필요한 유저");
      }
    }


    if (user != null) {
      //    토큰 생성.
      final String token = tokenProvider.create(user);

      log.info("usertoken={}", token);


      final UserDTO responsUserDTO = UserDTO.builder()
              .id(user.getId())
              .username(user.getUsername())
              .nickname(user.getNickname())
              .token(token)
              .build();

      return ResponseEntity.ok().body(responsUserDTO);
    } else {
      ResponseDTO responseDTO = ResponseDTO.builder()
              .error("Login Failed")
              .build();

      return ResponseEntity.badRequest().body(responseDTO);
    }

  }


  //조회
  // url로 접근할떄 토큰을 확인한다던가 보안성 로직이 필요할듯함?
  @GetMapping("/user/{id}")
  public UserEntity findbyId(@PathVariable Long id) {
    return userService.findById(id);
  }

  //수정
  //patch --> 엔티티의 일부만 업데이트하고싶을때
  //put --> 엔티티의 전체를 변경할떄
  //put 을 사용하면 전달한값 외는 모두 null or 초기값으로 처리된다고함..
  @PatchMapping("/user")
  public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
    try {
      UserEntity userEntity = UserEntity.builder()
              .id(userDTO.getId())
              .nickname(userDTO.getNickname())
              .password(passwordEncoder.encode(userDTO.getPassword()))
              .phoneNumber(userDTO.getPhoneNumber())
              .build();
      
      UserEntity updatedUser = userService.update(userEntity);

      UserDTO ResponseUserDTO = UserDTO.builder()
              .id(updatedUser.getId())
              .nickname(updatedUser.getNickname())
              .build();
      log.info("업데이트 완료");

      return ResponseEntity.ok().body(ResponseUserDTO);
    } catch (Exception e) {
      ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
      log.error("업데이트 실패");
      return ResponseEntity.badRequest().body(responseDTO);
    }

  }

  //삭제
  @DeleteMapping("/user")
  public ResponseEntity<?> deletUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal String userId) {
    System.out.println("userDTO = " + userDTO.toString());
    System.out.println("userId = " + userId);

    boolean checkPwd = userService.checkPwd(userDTO, userId);

    if (checkPwd == false) {
      return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다");
    } else {
      userService.deleteUser(Long.valueOf(userId));
      log.info("삭제완료");
      return ResponseEntity.ok().body("삭제 되었습니다.");
    }


  }


  //프론트에서 인가코드 받아오는 url
  /* 카카오 로그인 */
  @GetMapping("/kakao")
  public UserDTO kakaoCallback(@Param("code") String code) throws IOException {
    log.info("code={}", code);

    String[] access_Token = userService.getKaKaoAccessToken(code);
    String access_found_in_token = access_Token[0];
    // 배열로 받은 토큰들의 accsess_token만 createKaKaoUser 메서드로 전달
    UserDTO kakaoUser = userService.createKakaoUser(access_found_in_token);

    return kakaoUser;
  }

  // https://kauth.kakao.com/oauth/authorize?client_id=4af7c95054f7e1d31cff647965678936&redirect_uri=http://localhost:8080/auth/kakao&response_type=code


}