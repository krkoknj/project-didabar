package com.bitcamp221.didabara.websoket;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    @Autowired
    ChatService chatService;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {

        log.info("message.getmessage:" + message.getMessage());
        log.info("message.sender:" + message.getSender());
        log.info("messageRoomId:" + message.getRoomId());
        log.info("message:" + message.getType());
        log.info("messageUrl:"+message.getProfileImg_url());


        if (message.getType().equals(ChatMessage.MessageType.ENTER)) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.\n" + message.getMessage());
        }
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);

    }

    @MessageMapping("/alarm")
    public void alert(ChatMessage message){
        if(message.getType().equals(ChatMessage.MessageType.ALERT)){
            message.setMessage(message.getSender()+"님이 회원님에 카테고리에 입장하셧습니다");
        }
        sendingOperations.convertAndSend("/topic/invite/"+message.getNickname(),message);

    }


}
