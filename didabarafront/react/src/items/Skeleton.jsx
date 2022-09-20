import React from "react";
import styled from "styled-components";

const skeletionItems = [
  { id: 1 },
  { id: 2 },
  { id: 3 },
  { id: 4 },
  { id: 5 },
  { id: 6 },
  { id: 7 },
  { id: 8 },
  { id: 9 },
  { id: 10 },
  { id: 11 },
  { id: 12 },
];

const Container = styled.div`
  width: 100% - 40px;
  height: calc(100% -45px);
  padding: 0px 20px;
`;
const MenuBar = styled.div`
  width: 100%;
  margin-top: 10px;
  display: flex;
  border-radius: 8px;
  height: 50px;
  background-color: #e9e7e7;
  align-items: center;
  justify-content: space-between;
`;
const List = styled.ul`
  position: relative;
  list-style: none;
  padding-left: 0;
  display: flex;
  z-index: 0;
`;
const StyledSpan = styled.span`
  cursor: pointer;
  padding: 5px 15px;
  border-radius: 8px;
  margin: 5px;
  width: 80px;
  height: 40px;
  background-color: lightgray;
`;
const ListConatainer = styled.div`
  display: grid;
  align-self: center;
  gap: 10px;
  grid-template-columns: repeat(4, 250px);
  grid-template-rows: repeat(3, 200px);
  justify-content: center;
  margin-top: 15px;
  gap: 10px;
`;
const Boxing = styled.div`
  padding: 15px;
  display: flex;
  width: 200px;
  height: 200px;
  background-color: #e9e7e7;
  border-radius: 15px;
`;
function Skeleton() {
  return (
    <Container>
      <MenuBar>
        <List style={{ marginLeft: "15px" }}>
          <StyledSpan>{""}</StyledSpan>
          <StyledSpan>{""}</StyledSpan>
          <StyledSpan>{""}</StyledSpan>
        </List>
        <List>
          <StyledSpan>{""}</StyledSpan>
          <StyledSpan>{""}</StyledSpan>
          <StyledSpan>{""}</StyledSpan>
        </List>
        <List>
          <StyledSpan>{""}</StyledSpan>
        </List>
      </MenuBar>
      <ListConatainer>
        {skeletionItems.map((item) => {
          return (
            <Boxing key={item.id}>
              <StyledSpan style={{ height: "1rem" }}>{""}</StyledSpan>
            </Boxing>
          );
        })}
      </ListConatainer>
    </Container>
  );
}

export default Skeleton;
