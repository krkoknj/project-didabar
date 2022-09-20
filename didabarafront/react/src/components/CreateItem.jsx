import axios from "axios";
import React, { useRef, useState } from "react";
import { useSetRecoilState } from "recoil";
import styled, { keyframes } from "styled-components";
import { REQUEST_ADDRESS } from "../config/APIs";
import { didabaraItemState } from "../config/Atom";
import ModalPopUp from "./ModalPopUp";

const Container = styled.div`
  padding: 20px;
`;
const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 5px;
  padding: 20px 10px;
`;
const ClosingButton = styled.button`
  position: absolute;
  width: 60px;
  height: 26px;
  font-weight: bold;
  border-radius: 13px;
  top: 10px;
  right: 15px;
  cursor: pointer;
`;
const TitleAndContent = styled.input`
  height: 50px;
  padding-left: 20px;
  border-radius: 10px;
  border: 1px solid #2f3640;
  color: #2f3640;
  position: relative;
  width: 100%;
  margin-bottom: 10px;
  &:valid {
    color: #1976d2;
  }
  &:focus {
    outline: none;
  }

  &.title {
    font-size: 1rem;
  }
  &.content {
    font-size: 1rem;
  }
  &:valid ~ span,
  &:focus ~ span {
    color: #1976d2;
    font-size: 0.72rem;
    transform: translateX(15px) translateY(-5px);
    padding: 0px 10px;
    background-color: white;
    border-left: 1px solid #1976d2;
    border-right: 1px solid #1976d2;
    letter-spacing: 0.2em;
  }
`;
const TitleAndContentArea = styled.textarea`
  padding-left: 20px;
  border-radius: 10px;
  border: 1px solid #2f3640;
  color: #2f3640;
  position: relative;
  width: 100%;
  margin-bottom: 10px;
  padding: 20px;

  &:valid {
    color: #1976d2;
  }
  &:focus {
    outline: none;
  }

  &.title {
    font-size: 1rem;
  }
  &.content {
    font-size: 1rem;
  }
  &:valid ~ span,
  &:focus ~ span {
    color: #1976d2;
    font-size: 0.72rem;
    transform: translateX(15px) translateY(-5px);
    padding: 0px 10px;
    background-color: white;
    border-left: 1px solid #1976d2;
    border-right: 1px solid #1976d2;
    letter-spacing: 0.2em;
  }
`;
const loading = keyframes`
  0%{ opacity: 0}
  50%{opacity: 1}
  100%{ opacity: 0}
  
`;

const Text = styled.span`
  position: absolute;
  left: 40px;
  transition: 0.3s;
  pointer-events: none;
  font-size: 1rem;
  text-transform: uppercase;
`;
const Loading = styled.div`
  position: relative;
  display: grid;

  justify-content: center;
`;

const LoadingTwo = styled.span`
  font-size: 2rem;
  z-index: 2;
  color: #1976d2;
  left: 0;
  overflow: hidden;
  animation: ${loading} 2s ease infinite;
`;
const InputDate = styled.input`
  background-color: #2f3640;
  color: #ffffff;
  border: none;
  outline: none;
  height: 40px;
  border-radius: 5px;
  padding: 15px;
  margin-bottom: 10px;

  &::-webkit-calendar-picker-indicator {
    position: absolute;
    left: 70%;
    background-color: #ffffff;
    padding: 10px 50px;
    border-radius: 3px;
    cursor: pointer;
  }
  &::-webkit-datetime-edit-text {
    -webkit-appearance: none;
    /* display: none; */
  }
`;

const InputFile = styled.input`
  background-color: #2f3640;
  color: #ddffdd;
  height: 40px;
  border-radius: 5px;
  font-weight: bold;

  &::-webkit-file-upload-button {
    height: 40px;
    width: 20%;
    background-color: #ddffdd;
    color: #2f3640;
    border: none;
    cursor: pointer;
  }
`;
function CreateItem({ id, control }) {
  const setDidabaraItem = useSetRecoilState(didabaraItemState);
  const [fileTransfer, setFileTransfer] = useState(false);

  const formRef = useRef();

  // useEffect(()=>{
  //   formRef.current.
  // },[])

  const eraiseInfomation = () => {
    formRef.current.reset();
  };
  const sendCreateRequest = (e) => {
    e.preventDefault();
    setFileTransfer(true);
    console.log("아이디", id);
    const originalData = new FormData(e.target);
    const REQUESTDATA = new FormData();

    const categoryItem = {
      title: originalData.get("title"),
      content: originalData.get("content"),
      expiredDate: originalData.get("expiredDate"),
    };

    const categoryItemDTO = JSON.stringify(categoryItem);
    REQUESTDATA.append(
      "categoryItemDTO",
      new Blob([categoryItemDTO], { type: "application/json" })
    );
    REQUESTDATA.append("file", originalData.get("file"));

    axios
      .post(REQUEST_ADDRESS + `categoryItem/create/page/${id}`, REQUESTDATA, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => {
        console.log("업로드 완료 :", res.data);
        eraiseInfomation();
        setFileTransfer(false);
        setDidabaraItem(res.data.resList);
        close();
      })
      .catch((err) => {
        eraiseInfomation();
        console.log(err);
      });
  };
  const close = () => {
    eraiseInfomation();
    setFileTransfer(false);
    control.current.style.display = "none";
  };
  return (
    <ModalPopUp width={"500px"} Overlay={true}>
      <Container>
        <StyledForm onSubmit={sendCreateRequest} ref={formRef}>
          <div>
            <TitleAndContent
              type="text"
              name="title"
              className="title"
              required
            ></TitleAndContent>
            <Text>title here</Text>
          </div>
          <div>
            <TitleAndContentArea
              type="text"
              name="content"
              className="content"
              required
              rows="5"
            ></TitleAndContentArea>
            <Text>content here</Text>
          </div>
          <InputFile type="file" name="file"></InputFile>
          <span>게시 기한</span>
          <InputDate type="date" name="expiredDate"></InputDate>
          <button type="submit">보내기</button>
        </StyledForm>
        <ClosingButton onClick={close}>X</ClosingButton>
        {fileTransfer && (
          <Loading>
            <LoadingTwo>UpLoading..</LoadingTwo>
          </Loading>
        )}
      </Container>
    </ModalPopUp>
  );
}

export default CreateItem;
