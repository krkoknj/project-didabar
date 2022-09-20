import React from "react";
import styled from "styled-components";

const H1 = styled.h1`
  font-size: 66px;
  font-weight: 700;
`;

/**텍스트를 하드코딩 해야할때 따로 컴포넌트로 빼서 import / export 하려고 만듬. */
function InfoHardCodingText() {
  return (
    <div>
      <H1>하이</H1>
    </div>
  );
}

export default InfoHardCodingText;
