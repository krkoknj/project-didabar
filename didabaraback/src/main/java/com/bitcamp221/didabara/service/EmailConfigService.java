package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.mapper.EmailConfigMapper;
import com.bitcamp221.didabara.mapper.UserMapper;
import com.bitcamp221.didabara.model.EmailConfigEntity;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.EmailConfigRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class EmailConfigService {

  private final JavaMailSender mailSender;
  @Autowired
  private EmailConfigRepository emailConfigRepository;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private EmailConfigMapper emailConfigMapper;
  @Autowired
  private UserRepository userRepository;


  /**
   * 작성자 : 김남주
   * 빨간줄 보이는게 맞습니다.
   *
   * @param mailSender
   */
  @Autowired
  public EmailConfigService(@Lazy JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  /**
   * 작성자 : 김남주
   * 메소드 기능 : 인증코드 받아서 체크하는 기능
   * 마지막 작성자 : 김남주
   *
   * @param emailAuthCodeMap // email, auth_code 필요
   * @return
   */
  public boolean checkEmail(Map<String, String> emailAuthCodeMap) {
    /*Iterator<String> iter = emailAuthCodeMap.keySet().iterator();

    while (iter.hasNext()) {
      String key = iter.next();
      String value = emailAuthCodeMap.get(key);

      System.out.println(key + " : " + value);
    }*/

    Map<String, String> haveAuthCodeUser = null;
    try {
      haveAuthCodeUser = userMapper.selectUsernameAndAuthCode(emailAuthCodeMap);
      String inputAuthCode = emailAuthCodeMap.get("authCode"); // 매개변수로 받은 auth code
      String authCodeDB = haveAuthCodeUser.get("auth_code"); // DB에서 찾은 auth code
      if (!inputAuthCode.equals(authCodeDB)) {
        throw new Exception("일치하지 않는 계정, 코드");
      }
      return true;
    } catch (Exception e) {
      String message = e.getMessage();
      log.error("checkEmail={}", message);
      return false;
    }
  }


  /**
   * 작성자 : 김남주
   * 메서드 기능 : 회원가입 후 auth_code 전송 메서드
   * 마지막 작성자 : 김남주
   * \
   *
   * @param email
   * @throws Exception
   */
  public void mailsend(String email) throws Exception {
    // 난수 발생
    String code = UUID.randomUUID().toString().substring(0, 6);
    log.info("uuid={}", code);

    // 이메일로 찾은 유저 객체
    UserEntity userIdByEmail = userMapper.selectUserIdByEmail(email);

    // 이메일로 찾은 유저 객체의 아이디 emailconfig 테이블에 저장
    Long id = userIdByEmail.getId();
    EmailConfigEntity checkEmailEntity = emailConfigMapper.selectEmailConfig(id);
    if (checkEmailEntity == null) {
      emailConfigMapper.saveUserIntoEmailconfig(id, code);
    } else {
      emailConfigMapper.updateUserIntoEmailconfig(id, code);
    }


    MimeMessage m = mailSender.createMimeMessage();
    MimeMessageHelper h = new MimeMessageHelper(m, "UTF-8");
    h.setFrom("akskflwn@naver.com"); // 보내는 사람의 email 적는곳
    h.setTo(email); // 매개변수로 받은 이메일에 코드를 보냄
    h.setSubject("인증 메일이 도착했습니다."); // 이메일 제목
    h.setText(code); // 이메일 본문에 적을 값
    mailSender.send(m);

  }

  public String checkCode(Map<String, String> emailAuthCodeMap) {
    UserEntity findUser = userRepository.findByUsername(emailAuthCodeMap.get("username"));
    EmailConfigEntity emailConfig = null;
    try {

      emailConfig = emailConfigRepository.findById(findUser.getId()).orElseThrow(() ->
              new IllegalArgumentException("해당 아이디가 없습니다."));

    } catch (IllegalArgumentException e) {
      String msg = e.getMessage();
      log.error("checkEmail={}", msg);
      throw new IllegalArgumentException("해당 아이디가 없습니다.");
    }
    emailConfig.setCheck(true);
    emailConfigRepository.save(emailConfig);

    return "코드 인증 확인";
  }
}