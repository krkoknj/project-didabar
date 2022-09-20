import React from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { loginState, userState } from "../config/Atom";
import bgImg from "../static/How-to-Write-a-Scope-of-Work-Document-Board.webp";

/**컴포넌트들 스타일 정의 */
const StyledGrid = styled.div`
  width: 100%;
  height: calc(100% - 45px);
  background-image: url("https://images.unsplash.com/photo-1468779036391-52341f60b55d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8cGFwZXIlMjB3b3JrfGVufDB8fDB8fA%3D%3D&w=1000&q=80");
  background-size: cover;
  display: flex;
`;

function IntroPartOne({ children }) {
  //유저 로그인상태 관리. 유저가 로그인이면 시작해보기 버튼은 대시보드 기능을하고
  //유저가 비 로그인 상태라면 시작해보기 버튼은 로그인창을 불러온다.
  const user = useRecoilValue(userState);
  const setLoginState = useSetRecoilState(loginState);
  const navi = useNavigate();

  return <StyledGrid>{children}</StyledGrid>;
}

export default IntroPartOne;
