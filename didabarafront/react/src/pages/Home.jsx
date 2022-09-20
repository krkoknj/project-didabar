import React from "react";
import IntroPartOne from "../components/IntroPartOne";
import styled, { keyframes } from "styled-components";
import { useSetRecoilState } from "recoil";
import { loginState } from "../config/Atom";
const intro = keyframes`
 0%{opacity :0}
 100%{opacity : 1}
`;
const Intro = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  padding: 15px;
  background-image: linear-gradient(
    to right,
    rgba(0, 0, 0, 0.6),
    rgba(242, 243, 245, 0.5)
  );
`;

const MessageBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-left: 30px;
  border-radius: 35px;
  padding: 35px;
  width: 40%;
  height: 30%;
`;
const Logo = styled.span`
  color: whitesmoke;
  padding: 15px;
  font-size: 2rem;
  animation: ${intro} 4s ease 2s backwards;
`;

const Span = styled.span`
  color: whitesmoke;
  padding: 15px;
  font-size: 2rem;
  animation: ${intro} 4s ease;
`;

const StyledButton = styled.button`
  margin-bottom: auto;
  width: 160px;
  height: 60px;
  border: 1px solid whitesmoke;
  color: whitesmoke;
  border-radius: 5px;
  background-color: transparent;
  font-weight: bold;
  letter-spacing: 2px;
  &:hover {
    background-color: whitesmoke;
    border: none;
    color: black;
  }
`;
const Img = styled.img`
  left: 250px;
  width: 800px;
  margin-left: 150px;
  margin-top: 100px;
`;
function Home() {
  const setLoginState = useSetRecoilState(loginState);

  return (
    <>
      <IntroPartOne>
        <Intro>
          <Img src="../didabara_logo.png" />

          <MessageBox>
            <Span>함께 공유하는</Span>
            <Span> 워크스페이스</Span>
          </MessageBox>
          <MessageBox>
            <Logo style={{ marginBottom: "auto", letterSpacing: "4px" }}>
              DIDABARA
            </Logo>

            <StyledButton
              onClick={() => {
                setLoginState(true);
              }}
            >
              시작하기
            </StyledButton>
          </MessageBox>
        </Intro>
        {/* <form onSubmit={doParticipate}>
          참가요청하기 <input name="inviteCode" type="text" />
          <button type="submit">참가하기</button>
        </form> */}
      </IntroPartOne>
    </>
  );
}

export default Home;
