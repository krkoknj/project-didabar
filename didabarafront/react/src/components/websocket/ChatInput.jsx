import React, { useEffect, useState } from "react";
import { Button, Grid, TextField } from "@mui/material";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { useRecoilValue, useSetRecoilState } from "recoil";
import {
  chattingSelector,
  chattingState,
  myListOrJoinList,
  userState,
} from "../../config/Atom";
import styled from "styled-components";
import { Send } from "@mui/icons-material";
import ChatMsg from "./ChatMsg";

const StyledButton = styled(Button)`
  && {
    width: 0%;
    height: 100%;
    color: #f1f3f5;
    border-radius: 0;
    background-color: rgb(47, 54, 64);
    &:hover {
      background-color: rgb(30, 37, 46);
    }
  }
`;

const StyledGrid = styled(Grid)`
  background-color: #dcdcdc;
`;
const StyledText = styled(TextField)({
  "& .MuiOutlinedInput-root": {
    "& fieldset": {
      borderColor: "transparent",
    },
    "&:hover fieldset": {
      borderColor: "transparent",
    },
    "&.Mui-focused fieldset": {
      borderColor: "transparent",
    },
  },
});

var stompClient = null;
let chatId = 1;
const ChatInput = ({}) => {
  const ctId = useRecoilValue(myListOrJoinList);
  console.log(ctId);
  const user = useRecoilValue(userState);
  const [privateChats, setPrivateChats] = useState(new Map());
  const [publicChats, setPublicChats] = useState([]);
  const [userData, setUserData] = useState({
    id: chatId++,
    catagoryId: ctId,
    receivername: "/app/chat/room/" + ctId,
    connected: false,
    message: "",
  });

  console.log(ctId);
  const side = "";
  const connect = () => {
    let Sock = new SockJS("http://127.0.0.1:8080/ws/chat");
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    setUserData({ ...userData, connected: true });
    stompClient.subscribe("/app/chat/room/" + ctId, onMessageReceived);
    stompClient.subscribe("/topic/chat/room/" + ctId, onMessageReceived);
    userJoin();
  };

  const userJoin = () => {
    console.log("userJoin 실행");
    var chatMessage = {
      id: userData.id,
      sender: user.nickname,
      type: "ENTER",
      roomId: ctId,
      message: userData.message,
      profileImg_url: user.profile_image_url + user.file_name,
    };
    stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
  };

  const onMessageReceived = (payload) => {
    var payloadData = JSON.parse(payload.body);
    console.log("onMessageReceived 실행");
    switch (payloadData.type) {
      case "ENTER":
        if (!privateChats.get(payloadData.sender)) {
          privateChats.set(payloadData.sender, []);
          setPrivateChats(new Map(privateChats));
          publicChats.push(payloadData);
          setPublicChats([...publicChats]);
        }
        break;
      case "TALK":
        publicChats.push(payloadData);
        setPublicChats([...publicChats]);
        break;
      default:
    }
  };

  const onPrivateMessage = (payload) => {
    console.log("onprivateMassage실행");
    var payloadData = JSON.parse(payload.body);
    if (privateChats.get(payloadData.sender)) {
      privateChats.get(payloadData.sender).push(payloadData);
      setPrivateChats(new Map(privateChats));
    } else {
      let list = [];
      list.push(payloadData);
      privateChats.set(payloadData.sender, list);
      setPrivateChats(new Map(privateChats));
    }
  };

  const onError = (err) => {
    console.log(err);
  };

  const handleMessage = (event) => {
    const { value } = event.target;
    setUserData({ ...userData, message: value });
  };
  const sendValue = (e) => {
    if (stompClient) {
      var chatMessage = {
        sender: user.nickname,
        message: userData.message,
        status: "MESSAGE",
        type: "TALK",
        roomId: ctId,
        profileImg_url: user.profile_image_url + user.file_name,
      };
      console.log(chatMessage);
      stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
      console.log("sendValue 실행입니다");
      setUserData({ ...userData, message: "" });
    } else {
      registerUser();
    }
  };

  const sendPrivateValue = () => {
    console.log("sendPrivateValue 실행입니다");

    if (stompClient) {
      var chatMessage = {
        sender: user.nickname,
        receiverName: user.nickname,
        message: userData.message,
        roomId: userData.catagoryId,
        type: "TALK",
      };

      privateChats.push(chatMessage);
      setPrivateChats(new Map(privateChats));
    }
    stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
    setUserData({ ...userData, message: "" });
  };

  const registerUser = () => {
    connect();
  };
  const handleOnKeyPress = (e) => {
    if (e.key === "Enter") {
      sendValue(); // Enter 입력이 되면 클릭 이벤트 실행
    }
  };
  return (
    <>
      <div>
        {publicChats?.map((chat, index) => {
          return (
            <ChatMsg
              key={index}
              id={index}
              side={chat.sender == user.nickname ? "right" : "left"}
              messages={[chat.message]}
              avatar={chat.profileImg_url}
            ></ChatMsg>
          );
        })}
      </div>
      <form onSubmit={sendValue}>
        <StyledGrid container justifyContent={"center"}>
          <Grid item xs={10} height={65}>
            <StyledText
              fullWidth
              multiline
              rows={1}
              type="text"
              placeholder="add.. message"
              value={userData.message}
              onChange={handleMessage}
              onKeyDown={handleOnKeyPress}
            />
          </Grid>
          <Grid item xs={2}>
            <StyledButton type="submit" variant="text">
              <Send />
            </StyledButton>
          </Grid>
        </StyledGrid>
      </form>
    </>
  );
};

export default ChatInput;
