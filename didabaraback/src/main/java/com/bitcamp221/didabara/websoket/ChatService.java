package com.bitcamp221.didabara.websoket;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatRoomRepository chatRoomRepository;


    public ChatMessage create(String message, String sender, Long roomId, ChatMessage.MessageType type) {
        ChatMessage chatMessage = ChatMessage.builder()
                .message(message)
                .sender(sender)
                .roomId(roomId)
                .type(type)
                .build();
        return chatRepository.save(chatMessage);
    }

    public boolean exist(String sender, Long roomId) {
        boolean flag = chatRepository.existsBySenderAndRoomId(sender, roomId);
        return flag;
    }

    public List<ChatMessage> findChatList(Long roomId) {
        validateId(roomId);

        return chatRepository.findAllByRoomId(roomId);
    }

    public void validateId(Long roomId) {
        String message = "없는 방";
        if (roomId == null) {
            throw new RuntimeException(message);
        }
    }
}
