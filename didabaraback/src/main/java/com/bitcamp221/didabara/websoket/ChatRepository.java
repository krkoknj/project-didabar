package com.bitcamp221.didabara.websoket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    ChatMessage findBySenderAndRoomId(String sender, Long roomId);

    Boolean existsBySenderAndRoomId(String sender, Long roomId);

    List<ChatMessage> findAllByRoomId(Long roomId);
}
