import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = `${process.env.VUE_APP_API_SERVICE_URL}`;

// [ GET 마지막으로 읽은 채팅 인덱스 가져오기]
async function getLastReadIdx(params) {
  var result = null;
  await api
    .get(`${baseURL}/last/${params}`)
    .then((response) => {
      console.log("마지막으로 읽은 채팅 인덱스", response);
      result = response.data.data;
    })
    .catch((error) => {
      console.log(error);
    });
  return await Promise.resolve(result);
}

// [ GET 읽지 않은 채팅 불러오기 ]
async function getNewChatMessage(params) {
  var result = null;
  await api
    .get(`${baseURL}/unread/${params.id}/${params.idx}`)
    .then((response) => {
      result = response.data.data;
    })
    .catch((error) => {
      console.log("새로운 메시지 출력 실패", error);
    });
  return await Promise.resolve(result);
}

// [ POST 마지막 메시지 저장 ]
async function saveLastMessage(data) {
  await api
    .post(`${baseURL}/last`, data)
    .then((response) => {
      console.log("마지막 메시지 >>", response);
    })
    .catch((error) => {
      console.log("메시지 저장 실패", error);
    });
}

export { getLastReadIdx, getNewChatMessage, saveLastMessage };
