import React from "react";
import { Grid } from "@mui/material";
import styled from "styled-components";

const StyledGrid = styled(Grid)`
  width: 100%;
  height: 100vh;
  background-color: lightBlue;
`;
function IntroPartTwo() {
  return <StyledGrid>{/**소개 페이지 2번 */}IntroPartTwo</StyledGrid>;
}

export default IntroPartTwo;
