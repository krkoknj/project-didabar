import React from "react";
import styled from "styled-components";
import ModalPopUp from "./ModalPopUp";

function DeleteModal() {
  return (
    <ModalPopUp width={"400px"} height={"300px"} Overlay={false}>
      DeleteModal
    </ModalPopUp>
  );
}

export default DeleteModal;
