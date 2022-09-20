import React from "react";
import styled from "styled-components";

const OverLay = styled.div`
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  left: 0;
  top: 0;
  transition: hidden 0.5s;
  overflow: scroll;
  z-index: 50;
`;

const Modal = styled.div`
  position: absolute;
  background-color: white;
  border-radius: 5px;
  left: 50%;
  top: 15%;
  transform: translateX(-50%);
  z-index: 50;
`;

function ModalPopUp({ children, width, height, Overlay }) {
  const controlShowOrNot = () => {};

  return (
    <>
      <OverLay
        onClick={controlShowOrNot}
        style={{ backgroundColor: Overlay ? "show" : "transparent" }}
      >
        <Modal style={{ width: width, height: height }}>{children}</Modal>
      </OverLay>
    </>
  );
}

export default ModalPopUp;
