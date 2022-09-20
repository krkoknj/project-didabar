import React from "react";
import styled from "styled-components";
import { Paper, Avatar } from "@mui/material";
import { myListOrJoinList } from "../config/Atom";
import { useSetRecoilState } from "recoil";
import { useNavigate } from "react-router-dom";

const StyledPaper = styled(Paper)`
  && {
    cursor: grab;
    border-radius: 2px;
    :hover {
      background-color: rgba(238, 238, 238, 1);
    }
    margin-bottom: 1px;
  }
`;
const Wrapper = styled.div`
  display: grid;
  grid-template-columns: 30% 70%;
  align-items: center;
  justify-content: space-around;
  padding: 8px 0px;
`;
const ImgBlock = styled.div`
  display: flex;
  padding: 0px 10px;
  border-right: 1px solid grey;
  /* margin: 5px; */
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;
const Img = styled(Avatar)`
  && {
    width: 50px;
    height: 50px;
  }
`;
const InfoLine = styled.div`
  display: flex;
  flex-direction: column;
  width: 200px;
  text-align: center;
  justify-content: space-around;
  height: 80px;
`;
const H4 = styled.h4`
  color: rgba(53, 59, 72, 1);
  margin-block-start: 0rem;
  margin-block-end: 0.2rem;
`;
const H5 = styled.h5`
  color: ${(props) => props.theme.normal_Text_Color};
  margin-block-start: 0rem;
  margin-block-end: 0.3rem;
`;

function Profile({ img, username, title, text, id }) {
  const navi = useNavigate();
  const setList = useSetRecoilState(myListOrJoinList);

  const goCategory = () => {
    console.log("getting my item list of categories...");
    setList(id);
    navi(`/dashboard/publicboard/${id}`, {
      state: {
        id: id,
      },
    });
  };

  return (
    <StyledPaper onClick={goCategory}>
      <Wrapper>
        <ImgBlock>
          <Img alt="Background Image" src={img} />
          <H4>{username}</H4>
        </ImgBlock>
        <InfoLine>
          <H4 style={{ marginLeft: "5px" }}>{title}</H4>
          <H5 style={{ marginLeft: "5px" }}>{text}</H5>
        </InfoLine>
      </Wrapper>
    </StyledPaper>
  );
}

export default Profile;
