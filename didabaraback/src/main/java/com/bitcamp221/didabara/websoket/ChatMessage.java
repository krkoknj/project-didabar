package com.bitcamp221.didabara.websoket;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK , ALERT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private MessageType type;
    //채팅방 ID
    @Column(name = "room_id")
    private Long roomId;
    @Column(name = "sender")
    private String sender;
    //내용
    @Column(name = "message")
    private String message;

    @Column(name="nickname")
    private String nickname;

    @Column
    private String profileImg_url;
}