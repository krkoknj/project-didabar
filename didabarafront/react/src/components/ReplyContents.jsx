import DeleteForeverOutlinedIcon from "@mui/icons-material/DeleteForeverOutlined";
import axios from "axios";
import React from "react";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import { REQUEST_ADDRESS } from "../config/APIs";
import { userState } from "../config/Atom";

const ReplyBox = styled.div`
  padding: 0px 10px;
`;
const Wrapper = styled.div`
  border-bottom: 1px solid grey;
  padding: 0px 10px;
`;
const WriterAndDate = styled.div`
  display: grid;
  grid-template-columns: 60% 20% 20%;
`;
const TextBox = styled.div`
  width: 100%;
  overflow: auto;
  word-break: break-all;
`;

function ReplyContents({ reply, replyControl }) {
  const user = useRecoilValue(userState);
  const options = {
    method: "DELETE",
    url: REQUEST_ADDRESS + `categoryItemReply/delete/${reply.id}`,
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
  };

  const deleteReply = () => {
    const bool = window.confirm("댓글을 삭제하시겠습니까?");
    if (bool) {
      axios
        .request(options)
        .then((res) => {
          replyControl(res.data);
        })
        .catch((err) => console.log(reply.id));
    }
  };

  return (
    <ReplyBox>
      <Wrapper>
        <WriterAndDate>
          <h5 style={{ lineHeight: "50%" }}>{reply.nickname}</h5>
          <p style={{ justifySelf: "end" }}>{reply.createdDate}</p>
          {reply.writer === user.id && (
            <DeleteForeverOutlinedIcon
              style={{
                justifySelf: "end",
                alignSelf: "center",
                cursor: "pointer",
              }}
              onClick={deleteReply}
            />
          )}
        </WriterAndDate>
        <TextBox>
          <p style={{ lineHeight: "100%" }}>{reply.content}</p>
        </TextBox>
      </Wrapper>
    </ReplyBox>
  );
}

export default ReplyContents;
