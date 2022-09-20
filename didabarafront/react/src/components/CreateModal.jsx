import { Button, FormLabel, TextField, Typography } from "@mui/material";
import React, { useRef, useState } from "react";
import styled from "styled-components";
import axios from "axios";
import Pallet from "./Pallet";
import { REQUEST_ADDRESS } from "../config/APIs";
import ModalPopUp from "./ModalPopUp";
import { useSetRecoilState } from "recoil";
import { didabaraState, myListOrJoinList } from "../config/Atom";
import { useLocation, useNavigate } from "react-router-dom";

const StyledForm = styled.form`
  display: flex;
  width: 100%;
  flex-direction: column;
  height: 100%;
  align-self: center;
  justify-content: space-around;
  gap: 10px;
`;

const StyledLabel = styled(FormLabel)`
  && {
    display: flex;
    border: 1px solid #c4c4c4;
    border-radius: 5px;
    height: 40px;
    align-items: center;
    justify-content: center;
    margin-top: 15px;
    &:hover {
      cursor: pointer;
    }
  }
`;
const StyledImg = styled.img`
  width: 100%;
  height: 50%;
  border: 1px solid #c4c4c4;
  border-radius: 5px;
  overflow: hidden;
  background-size: cover;

  @media screen and (max-width: 700px) {
    height: 400px;
    margin-top: 20px;
    margin-bottom: 20px;
  }
`;
const StyledDiv = styled.div`
  border: 1px solid #c4c4c4;
`;
const StyledTextField = styled(TextField)`
  && {
    align-self: center;
  }
`;
const StyledFile = styled.input`
  position: absolute;
  display: none;
`;

const StyledWrap = styled.div`
  width: 400px;
  height: 100%;
  padding: 15px 20px;
`;
const Wrap = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
`;
const InviteCode = styled.input`
  text-align: center;
  border: none;
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  font-size: 2rem;
  cursor: pointer;
  margin-bottom: 15px;
  width: 80%;
  background-color: #e4e4e4;
  border-radius: 5px;
  color: #6565f1;
  ::selection {
    background-color: transparent;
  }
  :focus {
    outline: none;
  }
`;
function CreateModal({ setShowing }) {
  const [inviteCode, setInviteCode] = useState("");
  const setDidabara = useSetRecoilState(didabaraState);
  const setList = useSetRecoilState(myListOrJoinList);
  const navi = useNavigate();
  const imgRef = useRef();

  const location = useLocation();

  console.log(location);

  const copyInviteCode = (e) => {
    e.target.select();
    document.execCommand("copy");
    alert("복사되었습니다.");
  };

  const outOfCreation = () => {
    setInviteCode("");
    setShowing(false);
  };

  const showFileImage = (e) => {
    // 파일 이름을 빈 입력창에 표시함.//
    const filename = e.target.files[0].name;
    e.target.nextElementSibling.innerHTML = `${filename}`;

    const Reader = new FileReader();
    Reader.readAsDataURL(e.target.files[0]);
    Reader.onload = function () {
      imgRef.current.src = Reader.result;
    };
  };

  const makeCategory = (e) => {
    e.preventDefault();
    const ctData = new FormData(e.target);

    const requestData = new FormData();

    const category = {
      title: ctData.get("title"),
      content: ctData.get("content"),
    };

    const categoryDTO = JSON.stringify(category);

    requestData.append(
      "categoryDTO",
      new Blob([categoryDTO], { type: "application/json" })
    );
    requestData.append("file", ctData.get("file"));

    axios
      .post(REQUEST_ADDRESS + "category/create", requestData, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
          "Content-Type": "multipart/form-data",
        },
      })
      .then((res) => {
        setDidabara((prev) => {
          const before = prev.create;
          return { ...prev, create: [...before, res.data] };
        });
        setInviteCode(res.data.inviteCode);
        setList(res.data.id);
        navi(`/dashboard/myboard/${res.data.id}`);
      })
      .catch((err) => console.log(err));
  };

  return (
    <>
      <ModalPopUp width={"400px"} height={"600px"} Overlay={true}>
        <StyledWrap>
          <StyledForm onSubmit={makeCategory}>
            <Typography> 제목</Typography>
            <StyledTextField
              type="text"
              name="title"
              label="제목을 입력해주세요."
              fullWidth
            />
            <Typography mt={1}>내용</Typography>
            <StyledTextField
              type="text"
              name="content"
              label="소개"
              fullWidth
            />
            <StyledLabel htmlFor="file">배경이미지 선택하기</StyledLabel>
            <StyledFile
              type="file"
              name="file"
              id="file"
              onChange={showFileImage}
            />
            <StyledDiv
              style={{
                height: "2rem",
                display: " flex",
                alignItems: "center",
                borderRadius: "5px",
                backgroundColor: " #f1f1f1",
                color: "RGB(220, 220, 220)",
              }}
              id="fileName"
            >
              Selected Images
            </StyledDiv>
            <StyledImg src="" alt="bakcground" ref={imgRef} />
            <Pallet imgRef={imgRef} />
            <div style={{ width: "100%" }}>
              <Button
                variant="outlined"
                style={{ width: "100%" }}
                type="submit"
              >
                <Typography>등록하기</Typography>
              </Button>
              <Button
                variant="outlined"
                style={{ width: "100%" }}
                onClick={() => {
                  setShowing(false);
                }}
              >
                <Typography>취소 / 나가기</Typography>
              </Button>
            </div>
          </StyledForm>
        </StyledWrap>
      </ModalPopUp>
      {inviteCode && (
        <ModalPopUp width={"600px"} height={"300px"} Overlay={false}>
          <Wrap>
            <h2>커뮤니티 생성 완료</h2>
            <h3>초대코드 : (클릭시 복사됩니다)</h3>
            <InviteCode
              type="text"
              value={inviteCode}
              readOnly
              onClick={copyInviteCode}
            />
            <Button
              variant="outlined"
              style={{ color: "grey", border: "1px solid grey", width: "80%" }}
              onClick={outOfCreation}
            >
              돌아가기
            </Button>
          </Wrap>
        </ModalPopUp>
      )}
    </>
  );
}

export default CreateModal;
