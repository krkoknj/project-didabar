package com.bitcamp221.didabara.websoket;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_name")
    private String roomName;


}