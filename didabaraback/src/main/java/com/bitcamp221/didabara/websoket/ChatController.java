//package com.bitcamp221.didabara.websoket;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//
//public class ChatController {
//
//  @Autowired
//  private SimpMessagingTemplate simpMessagingTemplate;
//
//  @Autowired
//  RoomRepository roomRepository;
//  @Autowired
//  private ChatService chatService;
//
//  @MessageMapping("/room/{roomId}") // /app/message
//  @SendTo("/room/{roomId}")
//  public ChatMessage test(@DestinationVariable Long roomId, ChatMessage message) {
//    //room Id 는 카테고리 아이템 아이디일것임
//    log.info("test 실행");
//
//    log.info("roomId:" + roomId);
//    log.info("message:" + message);
//
//    //나중에 카테고리 아이디로 바꿔주야함
//    Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
//    return ChatMessage.builder()
//            .roomId(roomId)
//            .sender(chat.getSender())
//            .message(chat.getMessage())
//            .build();
//  }
//
//  @MessageMapping("/private-message")
//  public Message recMessage(@Payload Message message) {
//    log.info("private-message 실행됨");
//    simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/send", message);
//    System.out.println(message.toString());
//    return message;
//  }
//}