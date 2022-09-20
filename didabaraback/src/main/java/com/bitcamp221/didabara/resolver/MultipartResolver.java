package com.bitcamp221.didabara.resolver;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MultipartResolver {

  @Bean
  public CommonsMultipartResolver multipartResolver() {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

    multipartResolver.setMaxUploadSize(10485760); //1024 * 1024 * 10 (최대 10MB)

    return multipartResolver;
  }
}
