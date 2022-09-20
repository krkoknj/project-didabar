import React from "react";
import { Button, Typography } from "@mui/material";
import PictureAsPdfSharpIcon from "@mui/icons-material/PictureAsPdfSharp";
import LoginSharpIcon from "@mui/icons-material/LoginSharp";
import LogoutSharpIcon from "@mui/icons-material/LogoutSharp";
import { useNavigate } from "react-router-dom";
import { useRecoilValue, useResetRecoilState, useSetRecoilState } from "recoil";
import {
  categoryItem,
  didabaraState,
  loginState,
  userState,
} from "../config/Atom";
import styled from "styled-components";

const StyledButton = styled(Button)`
  && {
    color: rgba(220, 221, 225, 1);
  }
`;

const TopContainer = styled.div`
  display: flex;

  justify-content: flex-start;
  align-items: center;
  height: 45px;
  z-index: 5;
  background-color: rgba(47, 54, 64, 1);
`;

const MenuBox = styled.div`
  display: flex;
  margin-left: auto;
`;

function NavigationBar() {
  const setLoginState = useSetRecoilState(loginState);

  const navi = useNavigate();

  /**로그아웃 버튼 이벤트시
   * Recoil 의 정보를 리셋시키는 함수.
   * user 의 defautl 값인 id:null 이 된다.
   */
  const userLogout = useResetRecoilState(userState);
  const resetDocument = useResetRecoilState(didabaraState);
  const resetItemList = useResetRecoilState(categoryItem);

  /** 이벤트에 따라 유저의 상태를 관리하기 위한 Recoil */
  const user = useRecoilValue(userState);
  return (
    <TopContainer>
      <PictureAsPdfSharpIcon
        style={{
          cursor: "pointer",
          color: "rgba(220, 221, 225, 1)",
          marginLeft: "15px",
        }}
        fontSize="large"
        onClick={() => {
          return user ? navi("/dashboard/myboard") : navi("/");
        }}
      />

      <MenuBox>
        {user ? (
          <StyledButton
            variant="black"
            onClick={() => {
              localStorage.removeItem("token");
              localStorage.removeItem("user");
              resetItemList();
              resetDocument();
              userLogout();
              navi("/");
            }}
          >
            <LogoutSharpIcon />
            <Typography ml={1}>logout</Typography>
          </StyledButton>
        ) : (
          <StyledButton
            variant="black"
            onClick={() => {
              setLoginState(true);
            }}
          >
            <LoginSharpIcon />
            <Typography ml={1}>login</Typography>
          </StyledButton>
        )}
        {user ? (
          <StyledButton
            variant="black"
            onClick={() => {
              navi("/mypage/main");
            }}
          >
            <Typography>mypage</Typography>
          </StyledButton>
        ) : (
          <StyledButton
            variant="black"
            onClick={() => {
              navi("/join");
            }}
          >
            <Typography>Join</Typography>
          </StyledButton>
        )}
      </MenuBox>
    </TopContainer>
  );
}

export default NavigationBar;
