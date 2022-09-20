import {
  Alert,
  AlertTitle,
  Button,
  Divider,
  Grid,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { Container } from "@mui/system";
import axios from "axios";
import React, { useState } from "react";
import { useRecoilState, useSetRecoilState } from "recoil";
import { REQUEST_ADDRESS } from "../config/APIs";
import { loginState, userState } from "../config/Atom";
import PersonIcon from "@mui/icons-material/Person";
import LockResetIcon from "@mui/icons-material/LockReset";
import styled from "styled-components";

const StyledButton = styled(Button)`
  && {
    background-color: #2f3640;
    color: #ffffff;
    border: 1px solid black;
    border-radius: 20px;
  }
`;

const StyledTextField = styled(TextField)({
  "& label.Mui-focused": {
    color: "rgba(47, 54, 64,1.0)",
  },
  "& .MuiOutlinedInput-root": {
    "&.Mui-focused fieldset": {
      borderColor: "rgba(47, 54, 64,1.0)",
    },
  },
});

const Background = styled.div`
  height: 100vh;
  background-color: #ebebeb;
`;

function FindInfo() {
  const [showInputCode, setShowInputCode] = useState(false);
  const [showPwdInputCode, setShowPwdInputCode] = useState(false);
  const [code, setCode] = useState();
  const [pwdCode, setPwdCode] = useState();
  const [tempPassword, setTempPassword] = useState();
  const [username, setUsername] = useRecoilState(userState);
  const [showUsername, setShowUsername] = useState(false);
  const [showTempPwd, setShowTempPwd] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const setLoginState = useSetRecoilState(loginState);

  /**
   *
   * 이메일 찾기
   */
  const sendUsernameAuthCode = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const userPhoneNum = data.get("userPhoneNum");
    const realName = data.get("realName");

    axios({
      method: "POST",
      url:
        REQUEST_ADDRESS +
        `sms/findusername?userPhoneNum=${userPhoneNum}&realName=${realName}`,
    })
      .then((res) => {
        setShowInputCode(true);
        setCode(res.data.code);
        setUsername(res.data.username);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const submitCode = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const inputCode = data.get("inputCode");

    if (code === inputCode) {
      setShowUsername(true);
    } else {
      setShowAlert(true);
    }
  };

  /**
   *
   * 비빌번호 찾기 (임시비밀번호 발급)
   */
  const sendPwdAuthCode = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const userPhoneNum = data.get("userPhoneNum");
    const realName = data.get("realName");
    const username = data.get("username");

    axios({
      method: "POST",
      url:
        REQUEST_ADDRESS +
        `sms/findpassword?userPhoneNum=${userPhoneNum}&realName=${realName}&username=${username}`,
    })
      .then((res) => {
        console.log(res.data);
        setShowPwdInputCode(true);
        setPwdCode(res.data.code);
        setTempPassword(res.data.tempPassword);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const authCode = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const inputPwdCode = data.get("inputPwdCode");

    if (inputPwdCode === pwdCode) {
      setShowTempPwd(true);
    } else {
      alert("틀림");
    }
  };

  return (
    <>
      <Background>
        <Grid
          component="nav"
          style={{ backgroundColor: "#efefef", height: "150px" }}
        >
          <Grid item style={{ marginBottom: "8%" }} align="center">
            <Grid>
              <Typography variant="h4">
                아이디와 비밀번호를 잊으셨나요?
              </Typography>
            </Grid>
            <Grid>
              <Typography>
                계정에 등록된 이름과 전화번호를 입력해 주세요.
              </Typography>
            </Grid>
          </Grid>
        </Grid>

        <Container
          component="main"
          maxWidth="lg"
          style={{ marginTop: "3%", backgroundColor: "white" }}
        >
          <Paper>
          <Grid container justifyContent="center">
            <Container maxWidth="xs" justifyContent="center">
              <Grid container justifyContent="center">
                <Grid item xs={12}>
                  <Typography variant="h5" align="center" mb={2}>
                    <PersonIcon />
                    &nbsp;아이디 찾기
                  </Typography>
                </Grid>
                <form onSubmit={sendUsernameAuthCode}>
                  <Grid container style={{ gap: "15px" }} align="center">
                    <Grid item xs={12}>
                      <StyledTextField
                        label="이름"
                        name="realName"
                        type="text"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <StyledTextField
                        label="전화번호"
                        name="userPhoneNum"
                        type="text"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <StyledButton type="submit">인증번호 전송</StyledButton>
                    </Grid>
                  </Grid>
                </form>
                {showInputCode && (
                  <form onSubmit={submitCode}>
                    <StyledTextField
                      label="인증번호 입력"
                      name="inputCode"
                      type="text"
                    />
                    <StyledButton type="submit">인증번호 제출</StyledButton>
                  </form>
                )}
                {showUsername && (
                  <Typography variant="h2">
                    이메일은{username}입니다.
                  </Typography>
                )}
                {showAlert && (
                  <Grid mt={2}>
                    <Alert variant="outlined" severity="error">
                      <AlertTitle>인증번호가 올바르지 않습니다.</AlertTitle>
                    </Alert>
                  </Grid>
                )}
              </Grid>
            </Container>

            <Divider orientation="vertical" flexItem />

            <Container maxWidth="xs">
              <Grid container justifyContent="center">
                <Grid item xs={12}>
                  <Typography variant="h5" align="center" mb={2}>
                    <LockResetIcon />
                    &nbsp;비밀번호 찾기
                  </Typography>
                </Grid>

                <form onSubmit={sendPwdAuthCode}>
                  <Grid container style={{ gap: "15px" }} align="center">
                    <Grid itme xs={12}>
                      <StyledTextField
                        label="이름"
                        name="realName"
                        type="text"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <StyledTextField
                        label="Email"
                        name="username"
                        type="text"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <StyledTextField
                        label="전화번호"
                        name="userPhoneNum"
                        type="text"
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <StyledButton type="submit">인증번호 전송</StyledButton>
                    </Grid>
                  </Grid>
                </form>

                {showPwdInputCode && (
                  <form onSubmit={authCode}>
                    <StyledTextField
                      label="코드입력"
                      name="inputPwdCode"
                      type="text"
                    />
                    <Button type="submit">인증번호 제출</Button>
                  </form>
                )}
                {showTempPwd && (
                  <Grid item>
                    <Typography>
                      임시비밀번호는 {tempPassword} 입니다.
                    </Typography>
                    <Typography>임시비밀번호로 로그인 해주세요.</Typography>
                  </Grid>
                )}
              </Grid>
            </Container>
          </Grid>
          </Paper>
        </Container>
      </Background>
    </>
  );
}

export default FindInfo;
