import { Container, Divider, Grid, Paper, Typography } from "@mui/material";
import axios from "axios";
import React from "react";
import { useRecoilValue } from "recoil";
import { didabaraItemState } from "../config/Atom";

function UploadedDocs() {
  const didabaraItem = useRecoilValue(didabaraItemState);

  return (
    <>
      <Container>
        <Grid style={{ margin: "3%" }}>
          <Typography variant="h4">업로드 문서</Typography>
          <Typography variant="subtitle1">
            내가 업로드한 문서의 목록입니다.
          </Typography>
          <Divider style={{ marginTop: "10px" }} />
        </Grid>
      </Container>

      <Container>
        <Paper elevation={1}>
          <Grid container>
            {didabaraItem.map((item, index) => {
              return (
                <Grid item xs={2} align="center">
                  <Grid item key={index} aligh="center">
                    <Grid item align="center">
                      <img src={item.preview} height="150px" width="100px" />
                      <Grid item xs={12} align="center">
                        <Typography variant="subtitle2" color="#2C3E50">
                          {item.title}
                        </Typography>
                      </Grid>
                      <Grid item xs={12} align="center">
                        <Typography variant="subtitle1">
                          {item.content}
                        </Typography>
                      </Grid>
                    </Grid>
                  </Grid>
                </Grid>
              );
            })}
          </Grid>
        </Paper>
      </Container>
    </>
  );
}

export default UploadedDocs;
