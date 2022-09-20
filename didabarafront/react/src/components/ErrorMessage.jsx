import { Alert } from "@mui/material";
import React from "react";

function ErrorMessage({ children }) {
  return (
    <Alert
      severity="error"
      style={{
        position: "absolute",
        width: "50%",
        left: " 50%",
        transform: "translateX(-50%)",
      }}
    >
      {children}
    </Alert>
  );
}

export default ErrorMessage;
