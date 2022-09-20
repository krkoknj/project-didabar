import { motion } from "framer-motion";
import React from "react";
import { useSetRecoilState } from "recoil";
import styled from "styled-components";
import { loginState } from "../config/Atom";
import LoginInput from "./LoginInput";

/**컴포넌트들 스타일 정의 */
const StyledOverLay = styled(motion.div)`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 3;
`;

const StyledForm = styled(motion.div)`
  width: 30%;
  height: 100vh;
  background-color: white;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
  right: 0;
  top: 0;
  transform-origin: right;
  min-width: 380px;
  z-index: 5;
  @media screen and (max-width: 768px) {
    min-width: 300px;
  }
`;

const Img = styled.img`
  position: absolute;
  left: 0;
  right: 0;
  margin: 0 auto;
  margin-top: 80px;
  width: 250px;
`;

/** 로그인 화면 에니메이션 정의 부분.
 * 초기 값은 아무것도 보이지 않는 상태에서 url 이 `localhost:3000/login` 으로 바뀌면
 * 로그인 페이지가 나타난다. x값으로 로그인폼을 나타내고 사라지게 함.
 */
const loginFormAnimation = {
  start: {
    x: 700,
  },
  show: {
    x: 0,
    transition: {
      duration: 0.5,
      type: "just",
    },
  },
  exit: {
    x: 700,
    transition: {
      duration: 0.5,
    },
  },
};
function Loginform() {
  const setLoginState = useSetRecoilState(loginState);

  /** 반투명 배경이미지 클릭시 유저를 home 으로 이동시킨다.
   * url 에서 /login 이 사라지므로 로그인폼이 사라지는 애니메이션을 작동시킨다.
   */
  const returnToHome = () => {
    setLoginState(false);
  };

  return (
    <>
      <StyledOverLay
        onClick={returnToHome}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.5 }}
      />

      <StyledForm
        variants={loginFormAnimation}
        initial="start"
        animate="show"
        exit="exit"
      >
        <Img src="./didabara_logo.png" />
        <LoginInput />
      </StyledForm>
    </>
  );
}

export default Loginform;
