package com.bitcamp221.didabara.websoket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Message {
  private String senderName;
  private String receiverName;
  private String message;
  private String date;
  private Status status;
}