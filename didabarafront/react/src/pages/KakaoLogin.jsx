/**카카오 로그인시 인가코드를 받아올 임시 주소 */

import axios from "axios";
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { REQUEST_ADDRESS } from "../config/APIs";
import { userState } from "../config/Atom";

function KakaoLogin() {
  const setUser = useSetRecoilState(userState);
  const navi = useNavigate();
  /**해당 페이지가 로딩되었다면 url 에 인가코드가 담기게 된다.  */
  useEffect(() => {
    /**인가코드를 추출할 변수 생성. url 주소를 가지고 있다. */
    const url = new URL(window.location.href);

    /**위에서 만든 URL 에서 code=  라고 써진 키값을 찾아서 벨류를 반환받음.
     * 콘솔창에서 확인가능.
     */
    console.log("인가코드 추출 : ", url.searchParams.get("code"));
    const code = url.searchParams.get("code");

    /**위에서 얻은 인가코드를 백엔드의 카카로 로그인주소로 보냄.
     * ok respone 확인하였고, 이후 작업 해야함(유저로그인시키기, 토큰 브라우저에 저장하기)
     */
    axios.get(`${REQUEST_ADDRESS}auth/kakao?code=${code}`).then((res) => {
      localStorage.setItem("token", res.data.token);
      axios
        .get(`${REQUEST_ADDRESS}userinfo`, {
          headers: {
            Authorization: "Bearer " + res.data.token,
          },
        })
        .then((response) => {
          setUser(response.data);
          navi("/dashboard");
        });
    });
  }, []);
  return <div>KakaoLogin</div>;
}

export default KakaoLogin;
