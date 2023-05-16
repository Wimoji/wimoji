import api from "../api";

interface ChatRoomReq {
  eid: number;
  title: string;
  limit: number;
}

interface PrevChat {
  rid: string;
  idx: number;
}

interface UnreadChat {
  rid: string;
  idx: number;
}

// POST 채팅방 생성
export const makeChatRoom = (data: ChatRoomReq) => {
  return api.post("/chat-service/", data);
};
// GET 이전 채팅 데이터 불러오기
export const getPrevChat = (params: PrevChat) => {
  return api.get(`/chat-service/read/${params.rid}/${params.idx}`);
};
// GET 마지막으로 읽은 채팅 인덱스 가져오기
export const getLastReadIdx = (params: string) => {
  return api.get(`/chat-service/last/${params}`);
};
// GET 읽지 않은 채팅 불러오기
export const getNewChatMessage = (params: UnreadChat) => {
  return api.get(`/chat-service/unread/${params.rid}/${params.idx}`);
};
// POST 마지막 메시지 저장
export const saveLastMsg = (params: string) => {
  return api.post(`/chat-service/last/${params}`);
};
// DELETE 마지막으로 읽은 채팅 삭제
export const deleteLastMsg = (params: string) => {
  return api.delete(`/chat-service/last/${params}`);
};
