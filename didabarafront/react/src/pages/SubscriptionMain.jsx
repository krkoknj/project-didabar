import {
  Container,
  Divider,
  Grid,
  Paper,
  Tab,
  Typography,
} from "@mui/material";
import { TabContext, TabList, TabPanel } from "@mui/lab";
import { Box } from "@mui/system";
import React, { useState } from "react";
import MyCategoryList from "./MyCategoryList";
import FollowingLists from "./FollowingLists";

function SubscriptionList() {
  const [value, setValue] = useState("1");

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  return (
    <>
      <Container>
        <Grid style={{ margin: "3%" }}>
          <Typography variant="h4">커뮤니티 목록</Typography>
          <Typography variant="subtitle1">
            구독하고 있는 커뮤니티의 목록입니다.
          </Typography>
          <Divider style={{ marginTop: "10px" }} />
        </Grid>
      </Container>
      <Container>
        <Paper>
          <Box sx={{ typography: "body1" }}>
            <TabContext value={value}>
              <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
                <TabList
                  variant="fullWidth"
                  onChange={handleChange}
                  aria-label="lab API tabs example"
                >
                  <Tab label="내가 참여한 커뮤니티" value="1" />
                  <Tab label="내가 만든 커뮤니티" value="2" />
                </TabList>
              </Box>
              <TabPanel value="1">
                <FollowingLists />
              </TabPanel>
              <TabPanel value="2">
                <MyCategoryList />
              </TabPanel>
            </TabContext>
          </Box>
        </Paper>
      </Container>
    </>
  );
}

export default SubscriptionList;
