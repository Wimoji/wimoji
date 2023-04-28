import { apiInstance } from "@/api/index";

const api = apiInstance();
const baseURL = process.env.VUE_APP_API_USER_SERVICE_URL;

//[ POST /user-service/signup ] 회원 가입
async function signup(data, success, fail) {
  // console.log("회원 가입 보낼 정보 >> ", data);
  await api.post(`${baseURL}/signup`, data).then(success).catch(fail);
}

//[ PUT /user-service/login ] 로그인
async function login(data, success, fail) {
  // console.log("로그인 보낼 유저 정보 >> ", data);
  await api.put(`${baseURL}/login`, data).then(success).catch(fail);
}

//[ PUT /user-service/logout ] 로그아웃
async function logout(success, fail) {
  await api.put(`${baseURL}/logout`).then(success).catch(fail);
}

//[ DELETE /user-service/ ] 회원탈퇴
async function deleteUser(success, fail) {
  await api.delete(`${baseURL}/`).then(success).catch(fail);
}

export { signup, login, logout, deleteUser };
