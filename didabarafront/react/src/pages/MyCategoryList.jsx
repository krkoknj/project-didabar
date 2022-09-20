import {
  Avatar,
  Grid,
  IconButton,
  Modal,
  Divider,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Box,


  Button,
} from "@mui/material";
import React from "react";
import { useState } from "react";
import { useRecoilValue } from "recoil";
import { didabaraState } from "../config/Atom";
import ManageAccountsIcon from "@mui/icons-material/ManageAccounts";
import CategoryUserList from "../components/CategoryUserList";
import { useNavigate } from "react-router-dom";

const columns = [
  { id: "img", label: "", minWidth: 10 },
  { id: "category_id", label: "ID", minWidth: 10 },
  { id: "title", label: "이름", minWidth: 100 },
  { id: "count", label: "문서 수", minWidth: 50 },
  { id: "date", label: "개설일", minWidth: 70 },
  { id: "inviteCode", label: "초대코드", minWidth: 70 },
  { id: "memberEdit", label: "", minWidth: 20 },
];

export default function MyCategoryList() {
  const didabara = useRecoilValue(didabaraState);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [open, setOpen] = useState(false);
  const [categoryId, setCategoryId] = useState();
  const userListOpen = () => setOpen(true);
  const userListClose = () => setOpen(false);
  const navi = useNavigate();

  /**페이징 */
  const handleChangePages = (e, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (e) => {
    setRowsPerPage(+e.target.value);
    setPage(0);
  };

  /**스타일 컴포넌트 */

  const boxStyle = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    height: 500,
    bgcolor: "background.paper",
    p: 4,
  };

  return (
    <TableContainer component={Paper}>
      <Table stickyHeader aria-label="sticky table">
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
          {didabara.create.map((list, i) => (
            <TableRow
              key={i}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell>
                <Avatar src={list.profileImageUrl} />
              </TableCell>
              <TableCell>{list.id}</TableCell>
              <TableCell>{list.title}</TableCell>
              <TableCell>{list.count}</TableCell>
              <TableCell>{list.createdDate}</TableCell>
              <TableCell>{list.inviteCode}</TableCell>
              <TableCell>
                <IconButton
                  onClick={() => {
                    setCategoryId(list.id);
                    userListOpen();
                  }}
                >
                  <ManageAccountsIcon />
                </IconButton>
                <Modal
                  open={open}
                  onClose={userListClose}
                  BackdropProps={{
                    style: { backgroundColor: "rgba(0, 0, 0, 0.3)" },
                  }}
                >
                  <Box sx={boxStyle}>
                    <Grid container>
                      <Grid item xs={12}>
                        <Typography variant="h5">유저 정보</Typography>
                        <Grid item xs={12} mt={1} mb={2}>
                          <Divider />
                        </Grid>
                        <CategoryUserList categoryId={categoryId} />
                      </Grid>
                    </Grid>
                  </Box>
                </Modal>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
