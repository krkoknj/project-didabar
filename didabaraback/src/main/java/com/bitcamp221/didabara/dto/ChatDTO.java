package com.bitcamp221.didabara.dto;

import lombok.Data;

@Data
public class ChatDTO {
  //  통신시에 주고 받을 메세지 형식을 작성한다.
  private Long channelId;
  private Long writerId;
  private String chat;
}
