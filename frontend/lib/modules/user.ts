import api from "../api";

//user
interface SignupUser {
  nickname: string;
  uid: string;
  password: string;
}

interface SigninUser {
  uid: string;
  password: string;
}

// POST 회원가입
export const signUp = async (userData: SignupUser) => {
  return await api.post(`/user-service/signup`, userData);
};
// PUT 로그인
export const signIn = async (userData: SigninUser) => {
  return await api.put(`/user-service/login`, userData);
};
// PUT 로그아웃
export const logout = async () => {
  return await api.put(`/user-service/logout`);
};
// DELETE 회원탈퇴
export const deleteUser = async () => {
  return await api.delete(`/user-service/`);
};
//PUT 유저 정보에 채팅방 추가
export const myChat = (params: string) => {
  return api.put(`/user-service/chat/${params}`);
};
//GET 유저가 참가한 채팅방 불러오기
export const getUserChatRooms = () => {
  return api.get(`/user-service/chat`);
};
//DELETE 채팅방 삭제
export const removeChat = (params: string) => {
  return api.delete(`/user-service/chat/${params}`);
};
