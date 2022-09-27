package com.bitcamp221.didabara.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Map<String, String>> exceptionHandler(Exception e) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    log.info("controller advice 호출");

    Map<String, String> map = new HashMap<>();
    map.put("error type", status.getReasonPhrase());
    map.put("code", "400");
    map.put("message", e.getMessage());

    return new ResponseEntity<>(map, headers, status);
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> illegalException(IllegalArgumentException e) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    log.info("controller advice 호출");

    Map<String, String> map = new HashMap<>();
    map.put("error type", status.getReasonPhrase());
    map.put("code", "400");
    map.put("message", e.getMessage());

    return new ResponseEntity<>(map, headers, status);
  }

  @ExceptionHandler(value = IOException.class)
  public ResponseEntity<Map<String, String>> ioException(IOException e) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.BAD_REQUEST;

    log.info("controller advice 호출");

    Map<String, String> map = new HashMap<>();
    map.put("error type", status.getReasonPhrase());
    map.put("code", "400");
    map.put("message", e.getMessage());

    return new ResponseEntity<>(map, headers, status);
  }
}
