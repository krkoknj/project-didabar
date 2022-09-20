import { Avatar, Divider, Typography } from "@mui/material";
import React from "react";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";
import styled from "styled-components";

const StyledAvatar = styled(Avatar)`
  && {
    width: 60px;
    height: 60px;
    @media screen and (max-width: 700px) {
      width: 45px;
      height: 45px;
    }
  }
`;
function MypageNavHeader({ collapsed }) {
  const [userInfo] = useRecoilState(userState);
  return (
    <>
      <div style={{ padding: 16 }}>
        <StyledAvatar src={userInfo.profile_image_url + userInfo.file_name} />
        <div style={{ paddingBottom: 13 }} />
        <Typography variant="h6" noWrap>
          {userInfo.nickname}
        </Typography>
        <Typography color={"textSecondary"} noWrap gutterBottom>
          {userInfo.username}
        </Typography>
      </div>
      <Divider />
    </>
  );
}

export default MypageNavHeader;
