import { kakaoApi } from "../api";

interface Location {
  x: string; //longitude
  y: string; //latitude
}

// 카카오 로컬 API 사용. 경도 위도로 동코드 및 주소 검색
export const getAddressFromKakao = (params: Location) => {
  return kakaoApi.get(
    `/v2/local/geo/coord2regioncode.json?x=${params.x}&y=${params.y}`
  );
};
