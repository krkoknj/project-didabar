package com.bitcamp221.didabara.controller;


import com.bitcamp221.didabara.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class WebSocketTextController {

  @Autowired
  SimpMessagingTemplate template;

  @PostMapping("/send")
  public ResponseEntity<Void> sendMessage(@RequestBody MessageDTO messageDTO) {
    template.convertAndSend("/topic/message", messageDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @MessageMapping("/sendMessage")
  public void receiveMessage(@Payload MessageDTO messageDTO) {
    // receive message from client
  }


  @SendTo("/topic/message")
  public MessageDTO broadcastMessage(@Payload MessageDTO messageDTO) {
    return messageDTO;
  }
}