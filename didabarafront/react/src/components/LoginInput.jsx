import React from "react";
import { Button, Grid, Input, InputLabel, Typography } from "@mui/material";
import styled from "styled-components";
import { FormControl } from "@mui/material";
import { KakaoLoginAPI } from "../config/KakaoApi";
import axios from "axios";
import { useSetRecoilState } from "recoil";
import { loginState, userState } from "../config/Atom";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import ErrorMessage from "./ErrorMessage";
import { REQUEST_ADDRESS } from "../config/APIs";

/**컴포넌트 스타일 정의 */
const StyledInput = styled(FormControl)`
  && {
    width: 100%;
  }
`;
const StyledContainer = styled(Grid)`
  && {
    height: 100%;
    min-height: 685px;
    display: flex;
    justify-content: center;
    flex-direction: column;
  }
`;
const StyledDiv = styled.div`
  width: 60%;
  margin-left: 50%;
  margin-top: 50px;
`;
const StyledGrid = styled(Grid)`
  && {
    width: 60%;
    margin-bottom: 20px;
    padding-top: 20px;
    display: flex;
    justify-content: center;
    align-self: center;
  }
`;
const StyledButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    border: black solid 1px;
  }
`;
const StyledForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
function LoginInput() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  /**유저 상태관리를 위한 Recoil 호출.
   * 괄호안에들어가는 userState 는 config 폴더의 Atom 에 정의해 두었습니다.*/
  const setUser = useSetRecoilState(userState);
  const setLoginState = useSetRecoilState(loginState);

  /**페이지 리디렉션용 useNavigate */
  const navi = useNavigate();

  /**온클릭 이벤트
   * 카카오 로그인용 새창을 띄운다.
   */
  const openKakaoLogin = () => {
    window.open(KakaoLoginAPI, "_self");
  };

  const openFindInfo = () => {
    navi("/find-info");
  };

  /**이벤트 발생 폼으로부터 데이터를 받아
   * 각각 username 과 password 로 할당하여
   * 백엔드 로그인 주소로 보내준다.
   *
   * respone 값이 200(ok)이고 응답데이터에 유저의 아이디가 비어있지 않으면
   * (response 가 200인데도 유저의 데이터가 null 인경우도 있을수 있으므로)
   *
   * Recoil 의 setUser 함수로 유저의 상태를 로그인 상태로 만든다.
   */
  const sendLoginRequest = (data) => {
    console.log(data);
    axios
      .post(REQUEST_ADDRESS + "auth/signin", data)
      .then((res) => {
        if (res.status === 200 && res.data.id) {
          localStorage.setItem("token", res.data.token);
          axios
            .get(`${REQUEST_ADDRESS}userinfo`, {
              headers: {
                Authorization: "Bearer " + res.data.token,
              },
            })
            .then((response) => {
              setUser(response.data);
            });

          setLoginState(false);
          navi("/dashboard");
        }
      })
      .catch((err) => {
        if (err.name === "AxiosError") {
          alert("아이디와 비밀번호를 확인해주세요");
        }
      });
  };

  return (
    <StyledContainer container>
      <StyledForm onSubmit={handleSubmit(sendLoginRequest)}>
        <StyledGrid item>
          <StyledInput variant="standard">
            <InputLabel htmlFor="username">Email</InputLabel>
            <Input
              {...register("username", {
                required: "이메일을 입력해주세요.",
                pattern: {
                  value:
                    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,5}$/i,
                  message: "이메일 형식에 맞게 입력하세요.",
                },
              })}
              id="username"
              name="username"
              autoComplete="email"
            />
          </StyledInput>
        </StyledGrid>
        <StyledGrid item>
          <StyledInput variant="standard">
            <InputLabel htmlFor="password">Password</InputLabel>
            <Input
              {...register("password", {
                minLength: {
                  value: 4,
                  message: "비밀번호는 8자이상 40자이하입니다.",
                },
                required: "비밀번호를 입력해 주세요",
              })}
              id="password"
              name="password"
              autoComplete="current-password"
              type="password"
            />
          </StyledInput>
        </StyledGrid>
        <StyledGrid container item justifyContent="center">
          <Grid item xs={6}>
            <StyledButton type="submit">
              <Typography>로그인</Typography>
            </StyledButton>
          </Grid>
          <Grid item xs={6}>
            <StyledButton
              onClick={() => {
                setLoginState(false);
                navi("/join");
              }}
            >
              <Typography>회원가입</Typography>
            </StyledButton>
          </Grid>
        </StyledGrid>
        <StyledGrid>
          <Grid item xs={12}>
            <StyledButton onClick={openKakaoLogin}>
              <Typography>kakao login</Typography>
            </StyledButton>
          </Grid>
        </StyledGrid>
        <StyledGrid>
          <Typography onClick={openFindInfo} style={{ cursor: "pointer" }}>
            아이디 또는 비밀번호 찾기
          </Typography>
        </StyledGrid>
        <StyledGrid>
          <Typography variant="body2" color="textSecondary" align="center">
            Copyright ©{" "}
            <i className="fa-brands fa-github" style={{ fontSize: "2rem" }}></i>
            {new Date().getFullYear()}
            BitCamp 221기 2022
          </Typography>
        </StyledGrid>
      </StyledForm>

      <StyledDiv>
        {errors?.username && (
          <ErrorMessage>{errors?.username?.message}</ErrorMessage>
        )}
        {errors?.password && (
          <ErrorMessage>{errors?.password?.message}</ErrorMessage>
        )}
      </StyledDiv>
    </StyledContainer>
  );
}

export default LoginInput;
