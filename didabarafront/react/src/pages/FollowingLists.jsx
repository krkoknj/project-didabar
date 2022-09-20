import {
  Avatar,
  Button,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import { Container } from "@mui/system";
import axios from "axios";
import React, { useEffect } from "react";
import { useState } from "react";
import { useRecoilValue } from "recoil";
import { REQUEST_ADDRESS } from "../config/APIs";
import FolderOpenIcon from "@mui/icons-material/FolderOpen";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const columns = [
  { id: "id", label: "번호", minWidth: 5 },
  { id: "img", label: "", minWidth: 5 },
  { id: "host", label: "이름", minWidth: 10 },
  { id: "title", label: "커뮤니티 이름", minWidth: 100 },
  { id: "content", label: "정보", minWidth: 100 },
];

const StyledButton = styled(Button)`
  && {
    width: 200px;
    color: black;
    border: black solid 1px;
  }
`;

function FollowingLists() {
  const [list, setList] = useState();
  const navi = useNavigate();
  const openInviteCode = () => {
    navi("");
  };

  useEffect(() => {
    axios({
      method: "GET",
      url: REQUEST_ADDRESS + "subscriber/myJoinList",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    }).then((res) => setList(res.data));
  }, []);

  return list?.length ? (
    <TableContainer component={Paper}>
      <Table stickyHeader>
        <TableHead>
          <TableRow>
            {columns.map((column) => (
              <TableCell
                key={column.id}
                style={{
                  minWidth: column.minWidth,
                  backgroundColor: "#f5f5f5",
                }}
              >
                {column.label}
              </TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {list &&
            list.map((item, i) => (
              <TableRow
                key={i}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell>{item.id}</TableCell>
                <TableCell>
                  <Avatar src={item.userProfileImageUrl + item.filename} />
                </TableCell>
                <TableCell>{item.nickname}</TableCell>
                <TableCell>{item.title}</TableCell>
                <TableCell>{item.content}</TableCell>
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
  ) : (
    <>
      <Container>
        <Grid container justifyContent="center" mt={10} mb={15}>
          <Grid item xs={12} align="center">
            <Grid item xs={12}>
              <img src="../empty-folder.png" width="180px" />
            </Grid>
            <Grid itme xs={12}>
              <Typography variant="h4">
                아직 참여한 커뮤니티가 없습니다.
              </Typography>
            </Grid>
            <Grid item xs={12} mt={1}>
              <Typography variant="h5">초대코드로 가입해 보세요.</Typography>
            </Grid>
            <Grid item xs={12} mt={3}>
              <StyledButton onClick={openInviteCode}>
                초대코드 입력하기
              </StyledButton>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    </>
  );
}

export default FollowingLists;
