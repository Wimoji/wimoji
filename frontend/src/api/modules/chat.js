import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = `${process.env.VUE_APP_API_SERVICE_URL}/chat-service`;
// const baseURL = process.env.VUE_APP_API_CHAT_SERVICE_URL; // 혜진 채팅 서버

// [ POST / ] 채팅방 생성
async function makeChatRoom(data, success, fail) {
  await api.post(`${baseURL}/`, data).then(success).catch(fail);
}

// [ GET ] 이전 채팅 데이터 불러오기
async function getPrevChat(params, success, fail) {
  await api
    .get(`${baseURL}/read/${params.rid}/${params.idx}`)
    .then(success)
    .catch(fail);
}

export { makeChatRoom, getPrevChat };
