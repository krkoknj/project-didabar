import React from "react";
import { useState } from "react";
import {
  Container,
  Grid,
  TextField,
  Button,
  Typography,
  Paper,
  Divider,
  FormControl,
  MenuItem,
  IconButton,
  Tooltip,
  Input,
  Alert,
} from "@mui/material";
import * as Yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { useForm, Controller } from "react-hook-form";
import { useRecoilState, useResetRecoilState } from "recoil";
import { userState } from "../config/Atom";
import styled from "styled-components";
import AvatarPicker from "../components/AvatarPicker";
import SettingsIcon from "@mui/icons-material/Settings";
import { Box } from "@mui/system";
import axios from "axios";
import { REQUEST_ADDRESS } from "../config/APIs";
import { useNavigate } from "react-router-dom";
import WarningIcon from "@mui/icons-material/Warning";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";

/** 스타일 컴포넌트 */
const StyledButton = styled(Button)`
  && {
    width: 80%;
    color: black;
    border: black solid 1px;
  }
`;

const StyledGrid = styled.div`
  display: grid;
  grid-template-columns: 40% 60%;
  align-items: center;
  @media screen and (max-width: 800px) {
    grid-template-columns: repeat(1, auto);
    align-items: center;
  }
`;

const StyledGrid2 = styled.div`
  @media screen and (max-width: 800px) {
    align-items: center;
  }
`;

const StyledAvatarGrid = styled.div`
  @media screen and (max-width: 800px) {
    margin-bottom: 40px;
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
  width: "80%",
});

const StyledPaper = styled(Paper)`
  && {
    padding: 60px 0px;
  }
`;

const StyledBox = styled(Box)`
  @media screen and (max-width: 800px) {
    width: 80%;
  }
`;

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex: 1;
  gap: 10px;
`;

const StyledGrid3 = styled(Grid)`
  @media screen and (max-width: 800px) {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex: 1;
  }
`;

function PersonalInfo() {
  /**
   * 유효성 검사
   */
  const validation = Yup.object().shape({
    nickname: Yup.string().matches(
      /^[a-zA-Zㄱ-힣0-9-_.]{2,15}$/,
      "닉네임은 2~15사이로 한글, 영문, 숫자, 특수문자(-_.)를 포함할 수 있습니다."
    ),
  });

  const validationPwd = Yup.object().shape({
    newPassword: Yup.string().matches(
      /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W))(?=.*[!@#$%^*+=-]).{8,40}$/,
      "비밀번호는 8~40자 사이로 영문, 숫자, 특수문자를 포함해야 합니다."
    ),
    confirmNewPassword: Yup.string().oneOf(
      [Yup.ref("newPassword"), null],
      "비밀번호가 일치하지 않습니다."
    ),
  });

  const {
    control,
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: yupResolver(validation) });

  const {
    register: registerPwd,
    handleSubmit: handleSubmitPwd,
    formState: { errors: errorsPwd },
  } = useForm({ resolver: yupResolver(validationPwd) });

  const [user, setUser] = useRecoilState(userState);
  const userLogout = useResetRecoilState(userState);
  const navi = useNavigate();
  let date = user.modified_date + "";
  let dateResult = date.slice(0, 10);

  /**
   * 비밀번호 변경부분
   */
  const [showSettingPwd, setShowSettingPwd] = useState(false);

  const showChangePassword = () => {
    setShowSettingPwd((prev) => !prev);
  };

  const changePassword = (formData) => {
    const currentPassword = formData.password;
    const password = formData.newPassword;
    const confirmNewPassword = formData.confirmNewPassword;

    if (password === confirmNewPassword) {
      axios
        .post(
          REQUEST_ADDRESS + "userinfo/changepassword",
          { currentPassword, password },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then((res) => {
          alert(res.data);
          setUser(...user, ...res.data);
        })
        .catch((err) => console.log(err));
    }
  };

  /**
   * 닉네임, 직업 변경부분
   */
  const updateProfile = (formData) => {
    const nickname = formData.nickname;
    const job = formData.job;
    const phoneNumber = formData.phone_number;

    axios
      .patch(
        REQUEST_ADDRESS + "userinfo",
        { ...user, nickname, job, phoneNumber },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      )
      .then((res) => setUser({ ...user, ...res.data }))
      .catch((err) => console.log(err));
  };

  /**
   * 탈퇴
   */
  const [deleteAlert, setDeleteAlert] = useState(false);

  const deleteConfirm = (e) => {
    setDeleteAlert(true);
  };

  const deleteAccount = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const password = data.get("password");

    axios
      .delete(REQUEST_ADDRESS + "auth/user", {
        data: { password },
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => navi("/deleted"))
      .catch((res) => alert(res.data));
  };

  const [showDeleteForm, setShowDeleteForm] = useState(false);

  const openDeleteForm = () => {
    setShowDeleteForm((prev) => !prev);
  };

  return (
    <div>
      <Container>
        <Grid style={{ margin: "3%" }}>
          <Typography variant="h4">개인 정보</Typography>
          <Typography variant="subtitle1">
            디다바라에서 사용되는 나의 정보입니다.
          </Typography>
          <Divider style={{ marginTop: "10px" }} />
        </Grid>
      </Container>

      <Container maxWidth="md">
        <StyledPaper elevation={3}>
          <StyledGrid container>
            <StyledAvatarGrid
              item
              xs={5}
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                flex: 1,
                justifySelf: "center",
                alignSelf: "start",
              }}
            >
              <AvatarPicker />
            </StyledAvatarGrid>
            <StyledGrid2
              item
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                flex: 1,
                gap: "20px",
              }}
            >
              <StyledTextField
                label="이메일"
                defaultValue={user.username}
                InputProps={{ readOnly: true }}
                variant="standard"
                name="email"
              />
              <StyledBox sx={{ display: "flex", alignItems: "flex-end" }}>
                <StyledTextField
                  label="비밀번호"
                  defaultValue={"******"}
                  InputProps={{ readOnly: true }}
                  variant="standard"
                />
                <Tooltip title="비밀번호 변경" placement="top-start">
                  <IconButton onClick={showChangePassword}>
                    <SettingsIcon
                      sx={{ color: "action.active", mr: 1, my: 0.5 }}
                    />
                  </IconButton>
                </Tooltip>
              </StyledBox>
              {showSettingPwd && (
                <Grid>
                  <StyledForm onSubmit={handleSubmitPwd(changePassword)}>
                    <StyledGrid3>
                      <Typography variant="body1">
                        <i
                          className="fa-solid fa-key"
                          style={{ color: "#c9c9c9" }}
                        ></i>
                        &nbsp;비밀번호 변경
                      </Typography>
                    </StyledGrid3>
                    <StyledGrid3>
                      <StyledTextField
                        label="현재 비밀번호"
                        type="password"
                        variant="standard"
                        name="password"
                        {...registerPwd("password")}
                      />
                    </StyledGrid3>
                    <StyledGrid3>
                      <StyledTextField
                        label="새로운 비밀번호"
                        type="password"
                        variant="standard"
                        name="newPassword"
                        {...registerPwd("newPassword")}
                        error={errorsPwd.newPassword ? true : false}
                        helperText="비밀번호는 8~40자 사이로 영문, 숫자, 특수문자를 포함해야 합니다."
                      />
                    </StyledGrid3>
                    <StyledGrid3>
                      <StyledTextField
                        label="비밀번호 확인"
                        type="password"
                        variant="standard"
                        name="confirmNewPassword"
                        {...registerPwd("confirmNewPassword")}
                        error={errorsPwd.confirmNewPassword ? true : false}
                      />
                    </StyledGrid3>
                    <StyledGrid3>
                      <StyledButton type="submit">Update Password</StyledButton>
                    </StyledGrid3>
                  </StyledForm>
                </Grid>
              )}
              <Typography variant="subtitle2">
                최종 변경일: {dateResult}
              </Typography>
              <StyledTextField
                label="이름"
                defaultValue={user.real_name}
                InputProps={{ readOnly: true }}
                variant="standard"
                name={user.real_name}
              />
              <StyledTextField
                label="전화번호"
                defaultValue={user.phone_number}
                variant="standard"
                name={user.phone_number}
                {...register("phone_number")}
              />
              <Grid container>
                <StyledForm
                  onSubmit={handleSubmit(updateProfile)}
                  style={{ gap: "20px" }}
                >
                  <StyledGrid3 item xs={12}>
                    <StyledTextField
                      label="닉네임"
                      defaultValue={user.nickname}
                      variant="standard"
                      name="nickname"
                      {...register("nickname")}
                      error={errors.nickname ? true : false}
                      helperText={errors.nickname?.message}
                    />
                  </StyledGrid3>
                  <FormControl>
                    <StyledGrid3 item xs={12}>
                      <Controller
                        control={control}
                        defaultValue={user.job || ""}
                        name="job"
                        render={({ field }) => (
                          <StyledTextField
                            {...field}
                            select
                            label="직업"
                            variant="standard"
                            name="job"
                          >
                            <MenuItem value="무직">무직</MenuItem>
                            <MenuItem value="학생">학생</MenuItem>
                            <MenuItem value="컴퓨터">컴퓨터/인터넷</MenuItem>
                            <MenuItem value="언론">언론</MenuItem>
                            <MenuItem value="공무원">공무원</MenuItem>
                            <MenuItem value="군인">군인</MenuItem>
                            <MenuItem value="서비스업">서비스업</MenuItem>
                            <MenuItem value="교육">교육</MenuItem>
                            <MenuItem value="금융">금융/증권/보험업</MenuItem>
                            <MenuItem value="유통업">유통업</MenuItem>
                            <MenuItem value="예술">예술</MenuItem>
                            <MenuItem value="의료">의료</MenuItem>
                            <MenuItem value="법률">법률</MenuItem>
                            <MenuItem value="건설업">건설업</MenuItem>
                            <MenuItem value="제조업">제조업</MenuItem>
                            <MenuItem value="부동산업">부동산업</MenuItem>
                            <MenuItem value="운송업">운송업</MenuItem>
                            <MenuItem value="농수산업">
                              농/수/임/광산업
                            </MenuItem>
                            <MenuItem value="가사">가사</MenuItem>
                            <MenuItem value="기타">기타</MenuItem>
                          </StyledTextField>
                        )}
                      ></Controller>
                    </StyledGrid3>
                  </FormControl>
                  <StyledGrid3 item xs={12}>
                    <StyledButton type="submit">Update Profile</StyledButton>
                  </StyledGrid3>
                </StyledForm>
              </Grid>
            </StyledGrid2>
          </StyledGrid>
        </StyledPaper>
        <Paper elevation={3} style={{ marginTop: "3%", padding: "20px" }}>
          <Container maxWidth="lg">
            <Grid container>
              <Grid item xs={12}>
                <Typography variant="h6">Delete Account</Typography>
              </Grid>
              <Grid item xs={11}>
                <Typography
                  variant="subtitle2"
                  mt={1}
                  mb={1}
                  style={{ color: "#CD201F" }}
                >
                  <WarningIcon fontSize="xs" />
                  탈퇴시 모든 카테고리와 문서가 지워집니다.
                </Typography>
              </Grid>
              <Grid item xs={1}>
                <IconButton onClick={openDeleteForm}>
                  <ChevronRightIcon fontSize="large" sx={{ my: -0.5 }} />
                </IconButton>
              </Grid>
              <Grid item xs={12}>
                <Divider />
              </Grid>
              {showDeleteForm && (
                <Grid item xs={12} mt={1}>
                  <Typography variant="subtitle2">
                    계속하려면 먼저 본인임을 인증하세요.
                  </Typography>
                  <form onSubmit={deleteAccount}>
                    <Grid container mt={1}>
                      <Grid item xs={6}>
                        <TextField
                          style={{ width: "80%" }}
                          name="password"
                          label="비밀번호"
                          variant="standard"
                          type="password"
                        />
                        <Input style={{ display: "none" }} />
                      </Grid>
                      <Grid item xs={6} mt={1}>
                        <StyledButton onClick={deleteConfirm}>
                          확인
                        </StyledButton>
                      </Grid>
                      <Grid item xs={12}>
                        {deleteAlert && (
                          <div style={{ marginTop: "5px" }}>
                            <Alert
                              severity="info"
                              action={
                                <Button
                                  type="submit"
                                  color="inherit"
                                  size="small"
                                >
                                  탈퇴하기
                                </Button>
                              }
                            >
                              정말 탈퇴하시겠습니까?
                            </Alert>
                          </div>
                        )}
                      </Grid>
                    </Grid>
                  </form>
                </Grid>
              )}
            </Grid>
          </Container>
        </Paper>
      </Container>
    </div>
  );
}

export default PersonalInfo;
