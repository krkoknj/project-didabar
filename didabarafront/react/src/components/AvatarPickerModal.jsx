import React from "react";
import {
  Avatar,
  Button,
  Card,
  FormLabel,
  Grid,
  IconButton,
  List,
  Typography,
} from "@mui/material";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import { userState } from "../config/Atom";
import ClearIcon from "@mui/icons-material/Clear";
import axios from "axios";
import { REQUEST_ADDRESS } from "../config/APIs";
import { useState } from "react";
import { useEffect } from "react";
import t from "prop-types";
import("screw-filereader");

/**
 * 스타일 컴포넌트
 */
const number = window.innerWidth;
const Background = styled.div`
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  left: 0;
  top: 0;
  transition: hidden 0.5s;
`;

const StyledForm = styled.form`
  display: grid;
  height: 100%;
  align-self: center;
  justify-content: center;
`;

const StyledCard = styled(Card)`
  && {
    display: grid;
    /* grid-template-columns: 55% 45%; */
    width: 40%;
    height: 70%;
    position: fixed;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
    top: 50%;
    transition: all 0.5s;
    padding: 20px 20px;
    overflow-y: auto;
    /* max-height: 70%;
    max-width: 40%; */
    @media screen and (max-width: 600px) {
      grid-template-columns: repeat(1, 1fr);
      overflow-y: scroll;
    }
  }
`;

const StyledImgCard = styled(Card)`
  && {
    display: grid;
    overflow: scroll;
    padding: 10px 10px;
  }
`;

const StyledAvatar = styled(Avatar)`
  && {
    width: 150px;
    height: 150px;
  }
`;

const StyledImg = styled.img`
  width: 100px;
`;

const StyledButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    border: black solid 1px;
  }
`;

const StyledLabel = styled(FormLabel)`
  && {
    display: flex;
    border: 1px solid #c4c4c4;
    border-radius: 5px;
    height: 40px;
    width: 100%;
    align-items: center;
    justify-content: center;
    margin-top: 15px;
    &:hover {
      cursor: pointer;
    }
  }
`;

const StyledFileInput = styled.input`
  position: absolute;
  display: none;
`;

/**
 * 프로필 이미지 미리보기
 */
export const AvatarPickerModal = (props) => {
  const [user, setUser] = useRecoilState(userState);
  const [img, setImg] = useState();
  const [file, setFile] = useState();
  const [preview, setPreview] = useState(
    `${user.profile_image_url + user.file_name}`
  );
  const imageRef = useRef();
  const clearFile = () => {
    if (file != 0) imageRef.current.value = "";
  };
  const navi = useNavigate();
  const { handleChangeImage, avatarImage } = props;

  useEffect(() => {
    if (!preview || avatarImage) {
      setPreview(URL.createObjectURL(avatarImage));
    }

    return () => {
      if (preview) URL.revokeObjectURL(preview);
    };
  }, [preview, avatarImage]);

  const renderImage = (fileObject) => {
    fileObject.image().then((img) => {
      const canvas = document.createElement("canvas");
      const ctx = canvas.getContext("2d");
      const maxWidth = 256;
      const maxHeight = 256;

      const ratio = Math.min(maxWidth / img.width, maxHeight / img.height);
      const width = (img.width * ratio + 0.5) | 0;
      const height = (img.height * ratio + 0.5) | 0;

      canvas.width = width;
      canvas.height = height;
      ctx.drawImage(img, 0, 0, width, height);

      canvas.toBlob((blob) => {
        const resizedFile = new File([blob], preview.name, fileObject);
        setPreview(URL.createObjectURL(resizedFile));
      });
    });
  };

  const handleChange = (event) => {
    const fileObject = event.target.files[0];
    if (!fileObject) return;
    renderImage(fileObject);
    setFile(fileObject);
  };

  const handleChangePreview = (event) => {
    clearFile();
    setImg(event.target.name);
    setPreview(`${user.profile_image_url + event.target.name}`);
  };

  /**
   * 프로필 이미지 변경 (post)
   */
  const updateProfileImg = (event) => {
    event.preventDefault();
    const file = new FormData(event.target);

    if (setFile) {
      axios
        .post(REQUEST_ADDRESS + "userinfo/updateprofile", file, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
            "content-Type": "multipart/form-data",
          },
        })
        .then((res) =>
          setUser({
            ...user,
            profile_image_url: res.data.substring(
              0,
              res.data.indexOf("myfile")
            ),
            file_name: res.data.substring(res.data.indexOf("myfile")),
          })
        )
        .catch((err) => console.log(err));
    }

    if (setImg) {
      axios
        .patch(REQUEST_ADDRESS + `userinfo/svg?svgname=${img}`, img, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => setUser({ ...user, ...res.data }))
        .catch((err) => console.log(err));
    }
  };

  /**
   * 이미지 파일 삭제
   */
  const deleteFile = (e) => {
    clearFile();
    setPreview(`${user.profile_image_url + user.file_name}`);
    if (!file || !img) {
      setPreview(`${user.profile_image_url + "default.jpg"}`);
      setImg("default.jpg");
    }
  };

  return (
    <>
      <Background style={{ width: number }}></Background>
      <StyledForm encType="multipart/form-data" onSubmit={updateProfileImg}>
        <StyledCard>
          <Grid container direction="row">
            <Grid item xs={11}>
              <Typography variant="h4">프로필 이미지 변경</Typography>
            </Grid>
            <Grid item xs={1}>
              <IconButton
                onClick={() => {
                  navi(-1);
                }}
              >
                <ClearIcon />
              </IconButton>
            </Grid>
          </Grid>

          <Grid
            item
            id="fileName"
            style={{
              display: "flex",
              alignItems: "center",
              borderRadius: "5px",
              backgroundColor: "lightgray",
            }}
          >
            <Grid item>
              <StyledAvatar src={preview} alt={"avatar"}></StyledAvatar>
            </Grid>
          </Grid>

          <Grid container maxWidth="xs">
            <Grid item xs={6}>
              <StyledLabel htmlFor="file">이미지 불러오기</StyledLabel>
              <StyledFileInput
                ref={imageRef}
                id="file"
                name="images"
                type="file"
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={6}>
              <StyledLabel onClick={deleteFile}>삭제하기</StyledLabel>
            </Grid>
          </Grid>
          <Grid container>
            <Typography variant="h5">기본 이미지에서 선택하기</Typography>
            <StyledImgCard>
              <List>
                <StyledImg
                  name="Profile1.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile1.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile2.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile2.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile3.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile3.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile4.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile4.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile5.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile5.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile6.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile6.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile7.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile7.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile8.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile8.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile9.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile9.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile10.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile10.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile11.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile11.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile12.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile12.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile13.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile13.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile14.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile14.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile15.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile15.svg"
                  onClick={handleChangePreview}
                />
                <StyledImg
                  name="Profile16.svg"
                  src="https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/Profile16.svg"
                  onClick={handleChangePreview}
                />
              </List>
            </StyledImgCard>
          </Grid>
          <Grid item>
            <StyledButton type="submit">등록하기</StyledButton>
          </Grid>
        </StyledCard>
      </StyledForm>
    </>
  );
};

AvatarPickerModal.prototype = {
  handleChangeImage: t.func.isRequired,
  avatarImage: t.object,
};

export default AvatarPickerModal;
