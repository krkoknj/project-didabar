import React from "react";
import styled from "styled-components";
import axios from "axios";
import { REQUEST_ADDRESS } from "../config/APIs";
import { useRecoilState } from "recoil";
import { didabaraItemState } from "../config/Atom";
import CreateItem from "./CreateItem";
import { useState } from "react";

const OutLineBox = styled.div`
  border-radius: 10px;
  background-color: #2f3640;
  color: #f1f3f5;
  padding: 0px 10px;
`;
const Menu = styled.ul`
  list-style: none;
  padding-left: 0;
`;
const Item = styled.li`
  padding: 5px 10px;
  border-radius: 10px;
  &:hover {
    background-color: #f1f3f5;
    color: #2f3640;
  }
`;
const InputBox = styled.div`
  position: absolute;
  left: 0;
  border-radius: 10px;
  width: 600px;

  background-color: #2f3640;
  padding: 10px;
`;
const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  gap: 5px;
  padding: 15px 10px;
`;
const TextArea = styled.textarea`
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
    transform: translateX(5px) translateY(-5px);
    padding: 0px 10px;
    background-color: white;
    border-left: 1px solid #1976d2;
    border-right: 1px solid #1976d2;
    width: 50px;
    height: 20px;
    letter-spacing: 0.2em;
  }
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
    transform: translateX(5px) translateY(-5px);
    padding: 0px 10px;
    background-color: white;
    border-left: 1px solid #1976d2;
    border-right: 1px solid #1976d2;
    width: 50px;
    height: 20px;
    letter-spacing: 0.2em;
  }
`;
const Text = styled.span`
  position: absolute;
  left: 40px;
  transition: 0.3s;
  pointer-events: none;
  font-size: 1rem;
  text-transform: uppercase;
`;
const StyledButton = styled.button`
  width: 100%;
  height: 40px;
  border-radius: 8px;
  border: none;
`;

function ItemMenu({ id, category }) {
  const [didabaraItems, setDidabaraItems] = useRecoilState(didabaraItemState);
  const [modifie, setModifie] = useState(false);

  const sendModifie = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const title = data.get("title");
    const content = data.get("content");

    axios
      .put(REQUEST_ADDRESS + `categoryItem/update/page/${id}`, data, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => console.log(res));
  };
  const itemDrop = () => {
    const select = window.confirm("삭제하시겠습니까?");

    if (select) {
      axios
        .delete(REQUEST_ADDRESS + `categoryItem/delete/item-page/${id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => {
          setDidabaraItems((prev) => {
            const before = prev.filter((item) => item.category !== category);
            return [...before, ...res.data.resList];
          });
        });
    }
  };
  return (
    <OutLineBox>
      <Menu>
        <Item
          onClick={() => {
            setModifie(true);
          }}
        >
          수정
        </Item>
        <Item onClick={itemDrop}>삭제</Item>
      </Menu>
      {modifie && (
        <InputBox>
          <StyledForm onSubmit={sendModifie}>
            <div>
              <TitleAndContent
                type="text"
                name="title"
                className="title"
                required
              />
              <Text>제목</Text>
            </div>
            <div>
              <Text>아합</Text>
              <TextArea
                name="content"
                required
                placeholder="내용을 입력하세요"
                rows="5"
              />
            </div>
            <StyledButton type="submit">수정</StyledButton>
            <StyledButton
              onClick={() => {
                setModifie(false);
              }}
            >
              취소
            </StyledButton>
          </StyledForm>
        </InputBox>
      )}
    </OutLineBox>
  );
}

export default ItemMenu;
