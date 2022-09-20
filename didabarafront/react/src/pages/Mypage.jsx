import { Grid } from "@mui/material";
import React from "react";
import { Outlet } from "react-router-dom";
import MypageLists from "../components/MypageLists";
import MypageNavHeader from "../components/MypageNavHeader";
import Footer from "../components/Footer";

function Mypage() {
  const viewHeight = window.outerHeight;
  return (
    <div>
      <Grid container>
        <Grid
          component="nav"
          style={{
            backgroundColor: "#f5f5f5",
            width: "15%",
            height: viewHeight,
            // position: "relative",
          }}
        >
          <MypageNavHeader />
          <MypageLists />
        </Grid>
        <Grid item style={{ width: "85%" }}>
          <Outlet />
        </Grid>
      </Grid>
    </div>
  );
}

export default Mypage;
