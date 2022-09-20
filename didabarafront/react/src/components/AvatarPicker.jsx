import { Avatar, Badge, Button, Typography } from "@mui/material";
import React from "react";
import { useRecoilValue } from "recoil";
import { userState } from "../config/Atom";
import styled from "styled-components";
import EditIcon from "@mui/icons-material/Edit";
import AvatarPickerModal from "./AvatarPickerModal";
import { useNavigate } from "react-router-dom";

const StyledAvatar = styled(Avatar)`
  && {
    width: 200px;
    height: 200px;
  }
`;

const StyledBadge = styled(Badge)({
  "& .MuiBadge-badge": {
    color: "yellow",
    backgroundColor: "#2f4667",
    width: "45px",
    height: "45px",
    top: "175px",
    right: "25px",
    borderRadius: "100%",
    border: "2px solid white",
  },
});

const StyledIconButton = styled(Button)`
  && {
    color: white;
  }
`;

function AvatarPicker() {
  const user = useRecoilValue(userState);
  const navi = useNavigate();
  const updateImage = () => {
    navi("/mypage/updateimage");
  };

  return (
    <div>
      <StyledBadge
        badgeContent={
          <StyledIconButton
          onClick={updateImage}>
            <EditIcon />
          </StyledIconButton>
        }
        overlap="circular"
      >
        <StyledAvatar
          src={user.profile_image_url + user.file_name}
        ></StyledAvatar>
      </StyledBadge>
    </div>
  );
}

export default AvatarPicker;