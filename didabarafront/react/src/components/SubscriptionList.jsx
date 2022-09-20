import axios from "axios";
import React, { useRef, useState } from "react";
import { useEffect } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import styled from "styled-components";
import { getDidabaraJoinItems, REQUEST_ADDRESS } from "../config/APIs";
import ChatInput from "./websocket/ChatInput";
import {
  didabaraItemState,
  didabaraSelector,
  didabaraState,
  menuState,
  myListOrJoinList,
} from "../config/Atom";
import ModalPopUp from "./ModalPopUp";

const Container = styled.div`
  width: 100% - 40px;
  height: calc(100% -45px);
  padding: 0px 20px;
  position: relative;
`;
const MenuBar = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid grey;
`;
const List = styled.ul`
  position: relative;
  list-style: none;
  padding-left: 0;
  display: flex;
  z-index: 0;
`;
const Item = styled.li`
  cursor: pointer;
  padding: 5px 15px;
`;
const Indicator = styled.span`
  height: 3px;
  border-radius: 3px;
  background-color: #1976d2;
  position: absolute;
  bottom: 0px;
  transition: 0.3s;
`;
const Div = styled.div`
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: 80% 20%;
  top: 0;
  z-index: 4;
`;
const Chat = styled.div`
  width: 100%;
  height: 100%;
  max-width: 100%;
  background-color: #eeeeee;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow-y: scroll;
  overflow: hidden;
`;
const ListConatainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 20%);
  grid-template-rows: repeat(3, 200px);
  justify-content: center;
  margin-top: 30px;
  gap: 10px;
`;
const PDF = styled.div`
  display: flex;
  position: relative;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  img {
    height: 125px;
    cursor: pointer;
  }

  div {
    width: 100%;
    display: flex;
    flex-direction: column;
  }
  div h4 {
    width: 60%;
    color: #2f3640;
    margin-block-start: 0.8rem;
    margin-block-end: 0.8rem;
    align-self: center;
    cursor: pointer;
  }
  div span {
    /* display: block; */
    width: 60%;
    height: 3rem;
    color: #2f3640;
    align-self: center;
    cursor: pointer;

    overflow: hidden; // 을 사용해 영역을 감출 것
    text-overflow: ellipsis; // 로 ... 을 만들기
    /* white-space: nowrap; //아랫줄 내려가기 막음 */
  }
`;
const Nullbox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  h4 {
    color: #5656fa;
    cursor: pointer;
  }
`;
const Nullsign = styled.span`
  font-size: 2rem;
`;
const ModalDiv = styled.div`
  background-color: #515d6e;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  border: 1px solid #1976d2;
  gap: 10 px;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
`;
const ModalCloseButton = styled.button`
  width: 90%;
  height: 40px;
  border-radius: 10px;
  border: none;
  margin-top: 10px;
  color: white;
  background-color: #2f3640;
  cursor: pointer;
  &:hover {
    background-color: #ffddff;
    color: black;
  }
`;

function SubscriptionList({ loading }) {
  const filteredList = useRecoilValue(didabaraSelector);
  const [didabara, setDidabara] = useRecoilState(didabaraState);
  const setDidabataItems = useSetRecoilState(didabaraItemState);
  const [list, setList] = useRecoilState(myListOrJoinList);
  const setMenu = useSetRecoilState(menuState);
  const [guest, setGuest] = useState([]);
  const param = useParams();
  const location = useLocation();
  const navi = useNavigate();
  const indicatorRef = useRef();
  const listingRef = useRef();

  const currentDocument = didabara.join.find(
    (page) => page.id == param.document
  );

  const printGuestList = () => {
    setList("M");

    axios
      .get(REQUEST_ADDRESS + `subscriber/list/${param.document}`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => {
        setGuest(res.data);
        console.log(res);
      })
      .catch((err) => console.log(err));
  };

  const handleMenuState = (e) => {
    setMenu(e.target.innerText);
    indicatorRef.current.style.left = e.target.offsetLeft + "px";
    indicatorRef.current.style.width = e.target.offsetWidth + "px";
  };

  useEffect(() => {
    if (!loading) {
      listingRef.current.click();
    }
  }, []);

  useEffect(() => {
    if (!didabara.join.length) {
      return;
    }
    if (didabara?.join.length) {
      navi(`/dashboard/publicboard/${didabara?.join[0].id}`);
      setList(didabara.join[0].id);
    }
  }, []);

  const cancleSubscribtion = () => {
    const bool = window.confirm("해당 커뮤니티 구독을 취소하시겠습니까");

    if (bool) {
      axios
        .delete(REQUEST_ADDRESS + `subscriber/delete/${location.state.id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => {
          setDidabara((prev) => {
            return { ...prev, join: res.data };
          });
          getDidabaraJoinItems().then((res) => {
            setDidabataItems(res.data.resList);
          });
          if (didabara?.join.length) {
            setList(didabara.join[0].id);
            navi(`/dashboard/publicboard/${didabara.join[0].id}`);
          }
          navi("/dashboard/publicboard");
        })
        .catch((err) => console.log(err));
    }
  };

  return (
    <Div>
      <Container>
        <h1>{currentDocument?.title}</h1>
        <MenuBar>
          <List onClick={handleMenuState}>
            <Item ref={listingRef}>Listing</Item>
            <Item>Out Dated</Item>
            <Item>All List</Item>
          </List>
          <List>
            <Item onClick={printGuestList}>Members</Item>
            <Item onClick={cancleSubscribtion}>구독 해지</Item>
          </List>
          <Indicator ref={indicatorRef}> </Indicator>
        </MenuBar>
        <ListConatainer>
          {filteredList ? (
            filteredList.map((item, idx) => {
              // if (item.category == param.document)
              return (
                <PDF height={125} key={idx}>
                  <img
                    src={item.preview}
                    onClick={() => {
                      navi(`/dashboard/pages/${item.id}`, {
                        state: { item: item },
                      });
                    }}
                  />

                  <div>
                    <h4>{item.title}</h4>
                    <span>{item.content}</span>
                  </div>
                </PDF>
              );
            })
          ) : (
            <Nullbox>
              <Nullsign>게시된 글이 존재하지 않습니다.</Nullsign>
            </Nullbox>
          )}
          {list === "M" && (
            <ModalPopUp width={700} height={400}>
              <ModalDiv>
                <div
                  style={{
                    height: "10%",
                    backgroundColor: "rgba(0,0,0,0.5)",
                    width: "90%",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    borderRadius: "10px",
                  }}
                >
                  <h3 style={{ color: "white" }}>현재 커뮤니티 참여 인원</h3>
                </div>
                <div
                  style={{
                    height: "70%",
                    backgroundColor: "rgba(0,0,0,0.5)",
                    marginTop: "10px",
                    width: "90%",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    borderRadius: "10px",
                    flexWrap: "wrap",
                  }}
                >
                  {guest?.map((g) => {
                    return (
                      <div
                        style={{
                          display: "flex",
                          justifyContent: "center",
                          alignItems: "center",
                        }}
                      >
                        <img width={40} src={g.profileImageUrl + g.filename} />
                        <span
                          style={{
                            color: "white",
                            margin: "5px",
                            fontSize: "1rem",
                          }}
                        >
                          {g.nickname}
                        </span>
                      </div>
                    );
                  })}
                </div>
                <ModalCloseButton
                  onClick={() => {
                    setList("Listing");
                  }}
                >
                  닫기
                </ModalCloseButton>
              </ModalDiv>
            </ModalPopUp>
          )}
        </ListConatainer>
      </Container>
      <Chat>
        <ChatInput id={param.document} />
      </Chat>
    </Div>
  );
}

export default SubscriptionList;
