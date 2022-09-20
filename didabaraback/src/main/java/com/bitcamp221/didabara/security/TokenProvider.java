package com.bitcamp221.didabara.security;

import com.bitcamp221.didabara.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@Component
public class TokenProvider {

  private static final String SECRET_KEY = "auewiogerietognjbkbgjioj5490j9e5itBSJTIJGODFJBG945gujirgjrgwg5";

  Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

  //  사용자 정보를 받아서 JWT 토큰 생성
  public String create(UserEntity userEntity) {
//    토큰 유효 기한 설정
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

//    JWT 토큰 생성
    return Jwts.builder()
//            해더(header)에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
            .signWith(key, SignatureAlgorithm.HS256)
//            페이로드(payload)에 들어갈 내용
            .setSubject(userEntity.getId().toString())
            .setIssuer("didabara app")
            .setIssuedAt(new Date())// 현재 시간
            .setExpiration(expiryDate) // 비교할 대상인 Token 생성시간
            .compact();
  }

  //  사용자로부터 토큰을 받아와 그 토큰을 가진 사용자 id를 추출한다.
//  토큰을 디코딩 및 파싱하여 토큰의 위조 여부를 확인하는 작업
  public String validateAndGetUserId(String token) {
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();

    return claims.getSubject();
  }

  //  사용자 정보를 받아서 JWT 토큰 생성
  public String kakaocreate(UserEntity userEntity) {
//    토큰 유효 기한 설정
    Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

//    JWT 토큰 생성
    return Jwts.builder()
//            해더(header)에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
            .signWith(key, SignatureAlgorithm.HS256)
//            페이로드(payload)에 들어갈 내용
            .setSubject(userEntity.getId().toString())
            .setIssuer("didabara app")
            .setIssuedAt(new Date())// 현재 시간
            .setExpiration(expiryDate) // 비교할 대상인 Token 생성시간
            .compact();
  }
}