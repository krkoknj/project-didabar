import { Button, TextField } from "@mui/material";

import React from "react";

function Create() {
  return (
    <div>
      <TextField placeholder />

      <form>
        <input type="file" />
        <Button type="submit">보내기</Button>
      </form>
    </div>
  );
}

export default Create;
