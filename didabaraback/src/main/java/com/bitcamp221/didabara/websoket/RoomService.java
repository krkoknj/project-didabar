package com.bitcamp221.didabara.websoket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    //채팅방 하나 불러오기
    public ChatRoom findById(Long roomId) {
        ChatRoom findRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("없는 방입니다"));
        return findRoom;
    }

    //채팅방 생성
    public ChatRoom createRoom(String roomname, Long roomId) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .roomName(roomname)
                .build();
        ChatRoom room = chatRoomRepository.save(chatRoom);

        return room;
    }
}
