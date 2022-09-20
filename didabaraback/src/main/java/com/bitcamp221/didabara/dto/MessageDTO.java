package com.bitcamp221.didabara.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDTO {

  private String username;
  private String content;
  private Date date;

  public MessageDTO(String username, String content, Date date) {
    this.username = username;
    this.content = content;
    this.date = date;
  }

}