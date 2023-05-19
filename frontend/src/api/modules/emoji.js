import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = `${process.env.VUE_APP_API_SERVICE_URL}/emoji-service`;

//[ POST /emoji-service/ ] 이모지 생성
async function makeEmoji(data, success, fail) {
  // console.log("이모지 보낼 정보 >> ", data);
  await api.post(`${baseURL}/`, data).then(success).catch(fail);
}

// [GET /emoji-service/ ] 유저의 이모지 불러오기
async function getEmojis() {
  // await api.get(`${baseURL}/`).then(success).catch(fail);
  var result = null;
  await api
    .get(`${baseURL}/`)
    .then(({ data }) => {
      // console.log("나의 이모지", data);
      if (data.success) {
        result = data.data;
      }
    })
    .catch((error) => {
      console.log(error);
    });
  return await Promise.resolve(result);
}

// [PUT /emoji-service/ ] 유저의 이모지 삭제하기
async function deleteEmoji(data, success, fail) {
  await api.put(`${baseURL}/del`, data).then(success).catch(fail);
}

// [GET /emoji-service/location ] 현재 위치의 이모지 목록 조회
async function getAroundEmojis(data) {
  var result = null;
  await api
    .post(`${baseURL}/location`, data)
    .then(({ data }) => {
      // console.log("사용자 주변 정보 이모지 >> ", data);
      if (data.success) {
        result = data.data;
      }
    })
    .catch((error) => {
      console.log(error);
    });
  return await Promise.resolve(result);
}

export { makeEmoji, getEmojis, deleteEmoji, getAroundEmojis };
