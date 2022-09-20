import React from "react";
import styled from "styled-components";
const StyledSelect = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;
const StyledCheck = styled.input`
  width: 40px;
  height: 40px;
  border-radius: 22px;
  appearance: none;
  border: none;
  &:checked {
    border: 4px solid black;
  }
`;

function Pallet({ imgRef }) {
  const colorSet = (e) => {
    imgRef.current.style.backgroundColor = e.target.value;
  };

  return (
    <StyledSelect>
      <StyledCheck
        name="color"
        value="#ef5777"
        type="radio"
        style={{ backgroundColor: "#ef5777" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#575fcf"
        type="radio"
        style={{ backgroundColor: "#575fcf" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#4bcffa"
        type="radio"
        style={{ backgroundColor: "#4bcffa" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#34e7e4"
        type="radio"
        style={{ backgroundColor: "#34e7e4" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#0be881"
        type="radio"
        style={{ backgroundColor: "#0be881" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#ffdd59"
        type="radio"
        style={{ backgroundColor: "#ffdd59" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#ff5e57"
        type="radio"
        style={{ backgroundColor: "#ff5e57" }}
        onClick={colorSet}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#d2dae2"
        type="radio"
        style={{ backgroundColor: "#d2dae2" }}
        onClick={colorSet}
      ></StyledCheck>
    </StyledSelect>
  );
}

export default Pallet;
