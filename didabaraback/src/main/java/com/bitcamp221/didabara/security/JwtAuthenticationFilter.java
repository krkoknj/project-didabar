package com.bitcamp221.didabara.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      // 요청에서 토큰 가져오기
      String token = parseBearerToken(request);

      log.info("Filter is running...");

      //토큰 검사하기 및 시큐리티 등록
      if (token != null && !token.equalsIgnoreCase("null")) {
        String userid = tokenProvider.validateAndGetUserId(token);
        log.info("Authenticated userID:" + userid);

        //인증완료 : SecurityContextHolder 에 등록해야 인증된 사용자로 판단
        AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userid, null, AuthorityUtils.NO_AUTHORITIES);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);

        SecurityContextHolder.setContext(securityContext);

      }
    } catch (Exception ex) {
      logger.error("Could not set user authentication in security context", ex);
    }
    filterChain.doFilter(request, response);
  }

  //http 요청의 헤더를 파싱해서 bearer 토큰을 리턴한다
  private String parseBearerToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;

  }
}