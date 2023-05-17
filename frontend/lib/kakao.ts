import axios from "axios";

const kakao = axios.create({
  baseURL: process.env.NEXT_PUBLIC_KAKAO_URL,
  // timeout: 5000,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
    Authorization: "KakaoAK " + process.env.NEXT_PUBLIC_KAKAO_API_KEY,
  },
});

interface Location {
  x: number; //longitude
  y: number; //latitude
}

// 카카오 로컬 API 사용. 경도 위도로 동코드 및 주소 검색
export const getAddressFromKakao = (params: Location) => {
  return kakao.get(
    `/v2/local/geo/coord2regioncode.json?x=${params.x}&y=${params.y}`,
    {
      baseURL: process.env.NEXT_PUBLIC_KAKAO_URL,
    }
  );
};

export default kakao;
