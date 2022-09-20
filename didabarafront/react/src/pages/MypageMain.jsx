import React from "react";
import { Avatar, Typography, Grid, Button } from "@mui/material";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";
import { useNavigate } from "react-router-dom";

function MypageMain() {
  const [userInfo, setUserInfo] = useRecoilState(userState);
  const navi = useNavigate();

  return (
    <Grid
      container
      style={{ display: "flex", justifyContent: "center" }}
      mt={2}
    >
      <Grid
        item
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: "20px",
        }}
      >
        <Avatar
          src={userInfo.profile_image_url + userInfo.file_name}
          sx={{ width: 150, height: 150 }}
        />
        <Typography variant="h3">안녕하세요. {userInfo.nickname}!</Typography>
        <Button
          onClick={() => {
            navi("/dashboard/create");
          }}
        >
          만들러 가기
        </Button>
        <Button>초대코드로 들어가기</Button>
      </Grid>
    </Grid>
  );
}

export default MypageMain;
