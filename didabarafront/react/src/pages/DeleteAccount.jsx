import { Grid, Typography } from "@mui/material";
import { Container } from "@mui/system";
import React from "react";
import styled, { keyframes } from "styled-components";

const Background = styled.div`
  height: 100vh;
  background-image: linear-gradient(
    to right top,
    #f0f0f0,
    #e5e4ef,
    #d8d9ed,
    #c8ceed,
    #b7c4ec,
    #b6c3eb,
    #b4c2e9,
    #b3c1e8,
    #c2c8e6,
    #cecfe3,
    #d8d7e2,
    #e0e0e0
  );
`;

const StyledDiv = styled.body`
  height: 75%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const floating = keyframes`
      ${"0"} {
        transform: translateX(0);    
    }
    ${"50%"} {
        transform: translateX(-15px);
    }
    ${"100%"} {
        transform: translateX(0);
    }
  `;

const StyledImg = styled.img`
  animation: ${floating} 2s ease infinite;
  width: 200px;
`;

function DeleteAccount() {
  return (
    <Background>
      <StyledDiv>
        <Container maxWidth="xs">
          <Grid container direction="column" justifyContent="center">
            <Grid item xs={12} style={{ textAlign: "center" }}>
              <StyledImg src="2339835.png" width="200px" />
            </Grid>
            <Grid item xs={12} style={{ textAlign: "center" }}>
              <Typography
                style={{ fontFamily: "Share Tech Mono" }}
                variant="h2"
              >
                Good bye
              </Typography>
            </Grid>
            <Grid item xs={12} style={{ textAlign: "center" }}>
              <Typography>탈퇴가 완료되었습니다.</Typography>
            </Grid>
          </Grid>
        </Container>
      </StyledDiv>
    </Background>
  );
}

export default DeleteAccount;
