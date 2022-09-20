import React from "react";
import { useQuery } from "react-query";
import { useRecoilState, useSetRecoilState } from "recoil";
import styled from "styled-components";
import { getDidabaraJoin, getDidabaraJoinItems } from "../config/APIs";
import { didabaraItemState, didabaraState } from "../config/Atom";

import Profile from "./Profile";

const Area = styled.div`
  background-color: #dcdcdc;
  padding: 2px;
  height: 100%;
`;

function DropBox() {
  //시작
  const [didabaraJoin, setDidabaraJoin] = useRecoilState(didabaraState);
  const setDidabaraItems = useSetRecoilState(didabaraItemState);

  const { isLoading: joinLoading } = useQuery("JoinedData", getDidabaraJoin, {
    refetchOnWindowFocus: false,
    retry: false,
    onSuccess: (data) => {
      console.log("Result of getting my Join List :", data);
      setDidabaraJoin((prev) => {
        return { ...prev, join: data.data };
      });
    },
  });
  const { isLoading: itemLoading } = useQuery(
    "itemData",
    getDidabaraJoinItems,
    {
      refetchOnWindowFocus: false,
      retry: false,
      onSuccess: (data) => {
        console.log("Result of getting my Join List :", data);
        setDidabaraItems([...data.data.resList]);
      },
    }
  );

  return itemLoading ? (
    "loading..."
  ) : (
    <Area>
      {didabaraJoin?.join.map((list, idx) => (
        <Profile
          key={idx}
          img={list.categoryProfileImageUrl}
          username={list.nickname}
          title={list.title}
          text={list.content}
          id={list.id}
        />
      ))}
    </Area>
  );
}

export default DropBox;
