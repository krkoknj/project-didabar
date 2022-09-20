/**
 * 카카오 로그인요청 API 키와 주소를 상수로 저장.
 * -이상돈-
 */
const API_KEY = "4af7c95054f7e1d31cff647965678936";
const REDIRECTION = "http://localhost:3000/kakaologin";

/**
 * 카카오 로그인 요청 주소.
 * 이용자는 해당 URI 로 이동합니다 (현재 새창띄우기)
 */
export const KakaoLoginAPI = `https://kauth.kakao.com/oauth/authorize?client_id=${API_KEY}&redirect_uri=${REDIRECTION}&response_type=code`;
