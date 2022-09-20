import React, { useState } from "react";
import { Button } from "@mui/material";
import styled from "styled-components";
import CreateModal from "../components/CreateModal";
import ShowMyList from "../components/ShowMyList";
import InviteInput from "../components/InviteInput";
import DropBox from "../components/DropBox";
import axios from "axios";
import { getDidabara, REQUEST_ADDRESS } from "../config/APIs";
import { didabaraState } from "../config/Atom";
import { useSetRecoilState } from "recoil";
import { useQuery } from "react-query";
import { useNavigate, Outlet, useMatch } from "react-router-dom";

const StyledButton = styled(Button)`
  && {
    width: 50%;
    height: 100%;
    border-radius: 0px;
    font-size: 1rem;
    font-weight: bold;
    &:hover {
      background-color: inherit;
      color: inherit;
    }
    border-bottom: none !important;
  }
`;

const ButtonMyList = styled(StyledButton)`
  && {
    background-color: ${(props) => (props.$mylist ? "#EAEBEC" : "#1c2027")};
    color: ${(props) => (props.$mylist ? "#1c2027" : "#959697")};
    border: ${(props) => (props.$mylist ? "3px solid #1976D2" : "none")};
  }
`;
const ButtonJoinList = styled(StyledButton)`
  && {
    background-color: ${(props) => (!props.$mylist ? "#EAEBEC" : "#232830")};
    color: ${(props) => (!props.$mylist ? "#1c2027" : "#959697")};
    border: ${(props) => (!props.$mylist ? "3px solid #1976D2" : "none")};
  }
`;

const FristGrid = styled.div`
  background-color: #f1f3f5;
  height: 100%;
  display: grid;
  grid-template-rows: 5% 87% 8%;
`;

const SecondGrid = styled.div`
  display: grid;
  height: 100%;
`;
const Container = styled.div`
  display: grid;
  overflow-y: scroll;
  overflow-x: hidden;
  grid-template-columns: 15% 85%;
  height: calc(100% - 45px);
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

function DashBoard() {
  const [makeCategory, setMakeCategory] = useState(false);
  const [invite, setInvite] = useState(false);
  const navi = useNavigate();
  const setDidabara = useSetRecoilState(didabaraState);
  const myBoardMatch = useMatch("dashboard/myboard/*");
  // const publicBoardMatch = useMatch("dashboard/publicboard/*");

  const { isLoading } = useQuery("didabara", getDidabara, {
    refetchOnWindowFocus: false,
    retry: false,
    onSuccess: (data) => {
      setDidabara((prev) => {
        return { ...prev, create: data.data };
      });
      navi("/dashboard/myboard");
    },
  });

  const fetche = () => {
    axios
      .get(REQUEST_ADDRESS + "categoryItem/myitemlist", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => console.log(res));
  };

  return (
    <Container>
      <FristGrid>
        <div>
          <ButtonJoinList
            onClick={() => {
              navi("/dashboard/publicboard");
            }}
            $mylist={myBoardMatch}
            value="joinList"
          >
            구독
          </ButtonJoinList>
          <ButtonMyList
            onClick={() => {
              navi("/dashboard/myboard");
            }}
            $mylist={myBoardMatch}
          >
            나의 커뮤니티
          </ButtonMyList>
        </div>

        <div>{myBoardMatch ? <ShowMyList /> : <DropBox />}</div>
        <div>
          {myBoardMatch ? (
            <Button
              variant="contained"
              style={{ width: "100%", height: "100%" }}
              onClick={() => {
                setMakeCategory(true);
              }}
            >
              커뮤니티 생성
            </Button>
          ) : (
            <Button
              variant="contained"
              style={{ width: "100%", height: "100%" }}
              onClick={() => {
                setInvite(true);
              }}
            >
              초대코드가 있으신가요?
            </Button>
          )}
        </div>
      </FristGrid>
      <SecondGrid style={{ position: "relative" }}>
        {makeCategory && <CreateModal setShowing={setMakeCategory} />}
        {invite && <InviteInput setInvite={setInvite} />}
        <Outlet />
        {/* {showList ? (
          <DocumentList loading={isLoading} />
        ) : (
          <SubscriptionList loading={isLoading} />
        )} */}
      </SecondGrid>
    </Container>
  );
}

export default DashBoard;
