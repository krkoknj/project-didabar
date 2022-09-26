package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.dto.UserAndUserInfoDTO;
import com.bitcamp221.didabara.mapper.UserInfoMapper;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.model.UserInfoEntity;
import com.bitcamp221.didabara.presistence.UserInfoRepository;
import com.bitcamp221.didabara.presistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {

  private final UserInfoRepository userInfoRepository;
  private final UserInfoMapper userInfoMapper;
  private final UserRepository userRepository;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public int updateMyPage(String id, UserAndUserInfoDTO uid) {

    UserAndUserInfoDTO byDTO = userInfoMapper.findByDTO(id);

    System.out.println("byDTO = " + byDTO);

    if (uid.getNickname() == null) {
      uid.setNickname(byDTO.getNickname());
    }

    if (uid.getJob() == null) {
      uid.setJob(byDTO.getJob());
    }

    if (uid.getRealName() == null) {
      uid.setRealName(byDTO.getRealName());
    }

    if (uid.getPhoneNumber() == null) {
      uid.setPhoneNumber(byDTO.getPhoneNumber());
    }

    if (uid.getPassword() == null) {
      uid.setPassword(byDTO.getPassword());
    } else {
      String encode = passwordEncoder.encode(uid.getPassword());

      uid.setPassword(encode);
    }


    return userInfoMapper.updateUserInfoDTO(id, uid);
  }


  public Map<String, UserInfoEntity> findByIdMyPage(Long id) {

    return userInfoMapper.findByMap(id);
  }


  public int delete(String id) throws Exception {
    int checkRow = userInfoMapper.deleteUserAndInfo(id);
    log.info("checkRow={}", checkRow);
    if (checkRow != 0) {
      return checkRow;
    } else {
      throw new Exception("삭제 실패");
    }
  }

  public boolean checkAndChange(String id, Map<String, String> map, PasswordEncoder passwordEncoder) throws Exception {

    UserEntity byId = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다."));
    String currentPassword = map.get("currentPassword");
    if (passwordEncoder.matches(currentPassword, byId.getPassword())) {
      String changePassword = map.get("password");
      String encodePassword = passwordEncoder.encode(changePassword);
      byId.setPassword(encodePassword);

      userRepository.save(byId);

      return true;
    } else {
      throw new Exception("패스워드가 일치하지 않습니다.");
    }
  }

  public UserInfoEntity svgUpdate(String svgName, String id) throws Exception {
    UserInfoEntity byId = null;
    try {
      byId = userInfoRepository.findById(Long.valueOf(id))
              .orElseThrow(() -> new RuntimeException("없는 사용자"));
    } catch (RuntimeException e) {
      String error = e.getMessage();
      log.error("svgUpdate error={}", error);
      throw new RuntimeException(e);
    }
    byId.setProfileImageUrl(byId.getProfileImageUrl());
    byId.setFilename(svgName);

    return userInfoRepository.save(byId);
  }

  public UserInfoEntity amdinCheckAndBan(String id, String userId) throws IllegalStateException {

    UserInfoEntity admin = userInfoMapper.findByIdInUserInfo(id);

    if (admin.getRole() != 1) {
      throw new IllegalArgumentException("관리자가 아닙니다.");
    }

    // 밴 체크
    UserInfoEntity byIdInUser = userInfoMapper.findByIdInUserInfo(userId);

    byIdInUser.setBan(!byIdInUser.isBan());
    return userInfoMapper.updateBan(byIdInUser);

  }

  public UserInfoEntity uploadRootFile(String id, MultipartFile files) throws IOException {
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

    return userInfoRepository.save(findUser);
  }
}