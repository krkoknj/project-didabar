import React from "react";
import { useEffect } from "react";
import { useRef } from "react";
import { useMatch, useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import styled from "styled-components";
import { myListOrJoinList } from "../config/Atom";

const Container = styled.div`
  border-radius: 0;
  cursor: pointer;
  padding: 5px 10px;
  background-size: cover;
  background-position: center center;
  margin-bottom: 1px;
  transition: all 0.3s ease-in-out;
  :hover {
    scale: 1.1;
  }
`;

const StyledWapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;
const Box = styled.div`
  display: flex;
  flex-direction: column;
`;

const StyledSpan = styled.span`
  display: flex;
  background-color: rgba(0, 0, 0, 0.3);
  color: white;
  border-radius: 5px;
  padding: 0px 10px;
  height: 25px;
  align-items: center;
  margin: 5px;
`;

function MyList({ title, content, imgSrc, id }) {
  const navi = useNavigate();
  const setList = useSetRecoilState(myListOrJoinList);

  const goCategory = () => {
    console.log("getting my item list of categories...");
    setList(id);
    navi(`/dashboard/myboard/${id}`);
  };

  return (
    <Container
      style={{ backgroundImage: `url(${imgSrc})` }}
      onClick={goCategory}
    >
      <StyledWapper>
        <Box>
          <StyledSpan>
            <h5
              style={{
                lineHeight: "50%",
                marginBlockStart: "1rem",
                marginBlockEnd: "1rem",
              }}
            >
              {title}
            </h5>
          </StyledSpan>
          <StyledSpan>
            <p style={{ lineHeight: "50%" }}>{content}</p>
          </StyledSpan>
        </Box>
      </StyledWapper>
    </Container>
  );
}

export default MyList;
