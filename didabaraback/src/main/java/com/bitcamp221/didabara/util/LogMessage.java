package com.bitcamp221.didabara.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogMessage {

  public static String infoJoin(final String message) {

    return message + " method join success";
  }

  public static String infoComplete(final String message) {

    return message + " method action complete";
  }

  public static String errorJoin(final String message) {

    return message + " method join failed";
  }

  public static String errorNull(final String message) {

    return message + " data is null";
  }

  public static String errorMismatch(final String message) {

    return message + " data if sentence mismatch";
  }

  public static String errorExist(final String message) {

    return message + " does not exist";
  }
}
