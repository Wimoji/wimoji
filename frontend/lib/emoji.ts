import api from "./api";

//GET /location
interface HomeReq {
  latitude: string;
  longitude: string;
  dongCode: string;
}

// interface HomeRes {
//   uid: string;
//   eid: string;
//   rid: string;
//   title: string;
//   latitude: string;
//   dongCode: string;
//   dis: number;
//   participant: number;
//   limit: number;
//   isEnter: boolean;
// }

interface KakaoLocal {
  x: number; //longitude
  y: number; //latitude
}

export const getAroundEmojis = async (homeReq: HomeReq) => {
  return await api
    .post("/emoji-service", homeReq)
    .then((res) => res.data)
    .catch((err) => err);
};

//카카오 API 요청
export const kakaoApi = async (params: KakaoLocal) => {
  return await api
    .get(`/v2/local/geo/coord2regioncode.json?x=${params.x}&y=${params.y}`, {
      baseURL: process.env.NEXT_PUBLIC_KAKAOMAP_BASE_URL,
      headers: {
        Authorization: "KakaoAK " + process.env.NEXT_PUBLIC_KAKAOMAP_API_KEY,
      },
    })
    .then((res) => res)
    .catch((err) => err);
};
