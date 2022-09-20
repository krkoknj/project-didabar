import React, { useState } from "react";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";
import { useForm, Controller } from "react-hook-form";
import {
  Container,
  TextField,
  Button,
  Grid,
  Select,
  MenuItem,
  InputLabel,
  FormControl,
  Typography,
  Tooltip,
  IconButton,
  InputAdornment,
} from "@mui/material";
import { VisibilityOff, Visibility } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { loginState } from "../config/Atom";
import styled from "styled-components";
import axios from "axios";
import { KakaoLoginAPI } from "../config/KakaoApi";
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

const StyledKakaoButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    background-color: #fee500;
    :hover {
      background-color: #fee500;
    }
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

const openKakaoLogin = () => {
  window.open(KakaoLoginAPI);
};

const Join = () => {
  /**
   * 회원가입 유효성 검사 (Yup)
   */
  const validationSchema = Yup.object().shape({
    username: Yup.string()
      .required("이메일을 입력해 주세요.")
      .email("올바른 형식으로 입력해 주세요."),
    password: Yup.string()
      .required("비밀번호를 입력해 주세요.")
      .matches(
        /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W))(?=.*[!@#$%^*+=-]).{8,40}$/,
        "비밀번호는 8~40자 사이로 영문, 숫자, 특수문자를 포함해야 합니다."
      ),
    confirmPassword: Yup.string()
      .required("비밀번호를 확인해 주세요.")
      .oneOf([Yup.ref("password"), null], "비밀번호가 일치하지 않습니다."),
    phoneNumber: Yup.string()
      .required("핸드폰번호를 입력해 주세요.")
      .matches(/^\d{3}\d{3,4}\d{4}$/, "정확한 번호를 입력해 주세요."),
    nickname: Yup.string()
      .required("닉네임을 입력해 주세요.")
      .matches(
        /^[a-zA-Zㄱ-힣0-9-_.]{2,15}$/,
        "닉네임은 2~15사이로 한글, 영문, 숫자, 특수문자(-_.)를 포함할 수 있습니다."
      ),
  });

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: yupResolver(validationSchema) });

  const setLoginState = useSetRecoilState(loginState);

  const navi = useNavigate();

  /**
   * 서버에 데이터 보내기
   */
  const onSubmit = (data) => {
    const username = data.username;
    const password = data.password;
    const realName = data.realName;
    const phoneNumber = data.phoneNumber;
    const nickname = data.nickname;

    join({ username, password, realName, phoneNumber, nickname });
  };

  function join(userDTO) {
    axios({
      method: "post",
      url: REQUEST_ADDRESS + "auth/signup",
      data: userDTO,
    })
      .then((response) => {
        if (response.status === 200 && response.data.username != null) {
          axios.get(`${REQUEST_ADDRESS}emailconfig/${response.data.username}`);
          navi(`/emailconfig/${response.data.username}`);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }

  /**
   * 비밀번호 보이기/숨기기 기능
   */
  const [showPassword, setShowPassword] = useState(false);

  function handleClickShowPassword() {
    setShowPassword(!showPassword);
  }

  const handleChange = (prop) => (event) => {
    setShowPassword({ ...showPassword, [prop]: event.target.value });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "5%" }}>
      <Grid container spacing={1} style={{ textAlign: "center" }}>
        <Grid item xs={12}>
          <img src="./didabara_logo.png" style={{ width: "180px" }} />
        </Grid>
        <Grid item xs={12}>
          <Typography component="h1" variant="h5" mb={2}>
            회원가입
          </Typography>
        </Grid>
      </Grid>

      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <StyledTextField
              id="username"
              name="username"
              label="Email"
              style={{ width: "100%" }}
              {...register("username")}
              error={errors.username ? true : false}
              helperText={errors.username?.message}
            />
          </Grid>

          <Grid item xs={12}>
            <StyledTextField
              id="password"
              name="password"
              label="비밀번호"
              type={showPassword ? "text" : "password"}
              style={{ width: "100%" }}
              {...register("password")}
              error={errors.password ? true : false}
              //   helperText={errors.password?.message}
              helperText="비밀번호는 8~40자 사이로 영문, 숫자, 특수문자를 포함해야 합니다."
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={handleClickShowPassword}
                      edge="end"
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              onChange={(event) => handleChange(event)}
            />
          </Grid>
          <Grid item xs={12}>
            <StyledTextField
              id="confirmPassword"
              name="confirmPassword"
              label="비밀번호 확인"
              type="password"
              style={{ width: "100%" }}
              {...register("confirmPassword")}
              error={errors.confirmPassword ? true : false}
              helperText={errors.confirmPassword?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <StyledTextField
              id="realName"
              name="realName"
              label="이름"
              style={{ width: "100%" }}
              {...register("realName")}
              error={errors.realName ? true : false}
              helperText={errors.realName?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <StyledTextField
              id="phoneNumber"
              name="phoneNumber"
              label="전화번호"
              style={{ width: "100%" }}
              {...register("phoneNumber")}
              error={errors.phoneNumber ? true : false}
              helperText={errors.phoneNumber?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <StyledTextField
              id="nickname"
              name="nickname"
              label="닉네임"
              style={{ width: "100%" }}
              {...register("nickname")}
              error={errors.nickname ? true : false}
              helperText={errors.nickname?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <StyledButton type="submit">가입하기</StyledButton>
          </Grid>
          <Grid item xs={12}>
            <StyledKakaoButton
              onClick={openKakaoLogin}
              startIcon={<img src="./kakao.png" style={{ width: "16px" }} />}
            >
              카카오로 시작하기
            </StyledKakaoButton>
          </Grid>
          <Grid item xs={12} style={{ textAlign: "center" }}>
            <span>
              이미 가입하셨나요?
              <span
                onClick={() => {
                  setLoginState(true);
                }}
                style={{
                  cursor: "pointer",
                  borderBottom: "1px solid black",
                  marginLeft: "5px",
                  color: "rgba(47, 54, 64,1.0)",
                }}
              >
                로그인 하기
              </span>
            </span>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
};

export default Join;
