import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = process.env.VUE_APP_API_EMOJI_SERVICE_URL;

//[ POST /emoji-service/signup ] 이모지 생성
async function makeEmoji(data, success, fail) {
  // console.log("이모지 보낼 정보 >> ", data);
  await api.post(`${baseURL}/`, data).then(success).catch(fail);
}

export { makeEmoji };
