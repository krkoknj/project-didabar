package com.bitcamp221.didabara.util;

public class EqualsCheck {

  public static boolean isHost(final String userId, final Long host) {
    if (Long.valueOf(userId) == host) {

      return true;
    } else {

      return false;
    }
  }
}
