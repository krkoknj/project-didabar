import React from "react";
import { useQuery } from "react-query";
import { useRecoilValue, useSetRecoilState } from "recoil";
import styled from "styled-components";
import { getDidabaraItems } from "../config/APIs";
import { didabaraItemState, didabaraState } from "../config/Atom";
import MyList from "./MyList";

const StyledGrid = styled.div`
  background-color: #dcdcdc;
  overflow-y: scroll;
  overflow-x: hidden;
  height: 100%;
  ::-webkit-scrollbar {
    width: 1px;
  }
  ::-webkit-scrollbar-button {
    width: 0;
    height: 0;
  }
  ::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background-color: gray;
  }
`;

function ShowMyList() {
  const didabara = useRecoilValue(didabaraState);
  const setDidabaraItems = useSetRecoilState(didabaraItemState);

  const { isLoading } = useQuery("getDidabiarItems", getDidabaraItems, {
    refetchOnWindowFocus: false,
    retry: false,
    onSuccess: (data) => {
      setDidabaraItems([...data.data.resList]);
    },
  });

  return (
    <StyledGrid>
      {didabara?.create
        ? didabara.create.map((list, idx) => (
            <MyList
              key={idx}
              title={list.title}
              content={list.content}
              imgSrc={list.profileImageUrl}
              id={list.id}
            />
          ))
        : null}
    </StyledGrid>
  );
}

export default ShowMyList;
