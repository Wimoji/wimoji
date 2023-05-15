import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = process.env.VUE_APP_KAKAOMAP_BASE_URL;

// [GET] 현재 경도, 위도에서 주소 정보 가져오기
async function getNowPosition(params, success, fail) {
  await api
    .get(
      `${baseURL}/v2/local/geo/coord2regioncode.json?x=${params.longitude}&y=${params.latitude}`
    )
    .then(success)
    .catch(fail);
}

export { getNowPosition };
