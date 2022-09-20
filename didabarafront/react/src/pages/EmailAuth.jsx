import React, { useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import {
  TextField,
  Grid,
  Container,
  Typography,
  Button,
  Alert,
  AlertTitle,
} from "@mui/material";
import axios from "axios";
import styled, { keyframes } from "styled-components";
import InfoOutlinedIcon from "@mui/icons-material/InfoOutlined";
import { REQUEST_ADDRESS } from "../config/APIs";

/**
 * 컴포넌트 스타일 정의
 */
const StyledButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    border: black solid 1px;
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

/**
 * 이미지 애니메이션 기능
 */
const floating = keyframes`
      ${"0"} {
        transform: translateY(0);    
    }
    ${"50%"} {
        transform: translateY(-15px);
    }
    ${"100%"} {
        transform: translateY(0);
    }
  `;

const StyledImg = styled.img`
  animation: ${floating} 2s ease infinite;
  width: 150px;
`;

const Background = styled.div`
  height: 100vh;
  background-image: linear-gradient(
    to right top,
    #ffa6d9,
    #f1aee8,
    #e2b7f4,
    #d2bffb,
    #c3c6ff,
    #b1d0ff,
    #a3d9ff,
    #9ce1ff,
    #9eedf5,
    #b1f5e8,
    #cefbdb,
    #eeffd5
  );
`;

const StyledDiv = styled.body`
  /* top: 50%;
    transform: translateY(50%); */
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

/**
 * 인증번호 확인
 */
function EmailAuth() {
  const params = useParams();
  const navi = useNavigate();
  const [showAlert, setShowAlert] = useState(false);

  function codeSubmit(e) {
    e.preventDefault();
    const data = new FormData(e.target);
    const authCode = data.get("authCode");

    axios({
      method: "post",
      url: REQUEST_ADDRESS + "emailconfig/check",
      data: {
        username: params.username,
        authCode: authCode,
      },
    })
      .then((response) => {
        alert(response.data);
        navi("/");
      })
      .catch((error) => {
        console.log(error);
        setShowAlert(true);
      });
  }

  /**
   * 인증번호 다시 받기
   */
  function resendAuthcode() {
    axios
      .get(`${REQUEST_ADDRESS}emailconfig/${params.username}`, {
        username: params.username,
      })
      .then((response) => {
        axios.get(`${REQUEST_ADDRESS}emailconfig/${response.params.username}`);
      });
  }

  return (
    <Background>
      <StyledDiv>
        <Container component="main" maxWidth="xs">
          <Grid container spacing={2}>
            <Grid item xs={12} style={{ textAlign: "center" }}>
              <StyledImg src="../image1.png" />
            </Grid>
            <Grid item xs={12} style={{ textAlign: "center" }}>
              <Typography>
                {params.username} 으로 인증코드를 전송했습니다.
              </Typography>
            </Grid>
            <Grid item xs={12}>
              <Typography component="h1" variant="h4">
                이메일 인증을 완료해 주세요.
              </Typography>
            </Grid>
          </Grid>

          <form onSubmit={codeSubmit}>
            <Grid container spacing={2}>
              <Grid item xs={12} />
              <Grid item xs={12}>
                <StyledTextField
                  required
                  id="authCode"
                  name="authCode"
                  label="인증코드 입력"
                  style={{ width: "100%" }}
                />
              </Grid>
              <Grid item xs={12}>
                <StyledButton type="submit">인증하기</StyledButton>
              </Grid>
            </Grid>
          </form>

          {showAlert && (
            <Grid mt={2}>
              <Alert variant="outlined" severity="error">
                <AlertTitle>
                  인증번호가 올바르지 않습니다. 다시 확인해 주세요.
                </AlertTitle>
                인증번호를 받지 못하셨나요?
                <span
                  onClick={resendAuthcode}
                  style={{
                    fontWeight: "bold",
                    borderBottom: "1px solid",
                    cursor: "pointer",
                  }}
                >
                  인증번호 다시 받기
                </span>
              </Alert>
            </Grid>
          )}
        </Container>
      </StyledDiv>
    </Background>
  );
}

export default EmailAuth;
