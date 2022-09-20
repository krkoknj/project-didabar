package com.bitcamp221.didabara.config;


import com.bitcamp221.didabara.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authz) -> authz
                    .anyRequest().authenticated()
            )
            .httpBasic().disable()
            .csrf().disable()
            .cors()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    //Jwt filter 등록
    //매 요청마다 jwtAuthenticationFilter 실행
    http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/**", "/auth/**", "home",
            "/emailconfig/**", "/userinfo/**", "/upload/**", "/pdfreport/**", "/test/**",
            "/send*,", "/topic/**", "/sms/**", "/notification/**", "/kafka/**", "/ws-chat/**", "/api/**", "/app/**");
  }
}
