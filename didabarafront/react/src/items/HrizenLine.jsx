/** 해당 컴포넌트는 실험적으로 만들어봄 */
import React from "react";
import styled from "styled-components";

const HorizenLine = styled.div`
  width: 100%;
  text-align: center;
  border-bottom: 1px solid #aaa;
  line-height: 0.1rem;
  margin: 10px 0 20px;
`;
const StyledSpan = styled.span`
  background-color: white;
  padding: 0 10px;
`;

const Line = ({ text, color = "#aaa" }) => {
  return (
    <HorizenLine
      style={{
        borderBottom: `1px solid ${color}`,
      }}
    >
      <StyledSpan>{text}</StyledSpan>
    </HorizenLine>
  );
};

export default Line;
