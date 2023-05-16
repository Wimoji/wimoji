import api from "../api";

interface HomeReq {
  latitude: string;
  longitude: string;
  dongCode: string;
}

interface EmojiSaveReq {
  eid: string;
  rid: string;
  title: string;
  latitude: string;
  longitude: string;
  dongCode: string;
}

interface EmojiDeleteReq {
  order: string;
  eid: string;
}

// POST 이모지 생성
export const makeEmoji = (data: EmojiSaveReq) => {
  return api.post("/emoji-service/", data);
};
// GET 유저의 이모지 불러오기
export const getEmojis = () => {
  return api.get("/emoji-service/");
};
// PUT 유저의 이모지 삭제하기
export const deleteEmoji = (data: any) => {
  return api.put("/emoji-service/del", data);
};
// GET 주변 이모지 불러오기
export const getAroundEmojis = (homeReq: HomeReq) => {
  return api.post("/emoji-service/location", homeReq);
};
