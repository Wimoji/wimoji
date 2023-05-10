import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = `${process.env.VUE_APP_API_SERVICE_URL}/chat-service`;

// [ POST / ] 채팅방 생성
async function makeChatRoom(data, success, fail) {
  await api.post(`${baseURL}/`, data).then(success).catch(fail);
}

// [ GET ] 이전 채팅 데이터 불러오기
// return chatList, firstIdx
async function getPrevChat(params, success, fail) {
  await api
    .get(`${baseURL}/read/${params.rid}/${params.idx}`)
    .then(success)
    .catch(fail);
}

export { makeChatRoom, getPrevChat };
