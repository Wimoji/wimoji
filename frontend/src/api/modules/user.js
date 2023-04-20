import { apiInstance } from "@/api/index";

const api = apiInstance();

//[ POST /user-service/signup ] 회원 가입
async function signup(data, success, fail) {
  // console.log("회원 가입 보낼 정보 >> ", data);
  await api.post(`/user-service/signup`, data).then(success).catch(fail);
}

//[PUT /user-service/login] 로그인
async function login(data, success, fail) {
  // console.log("로그인 보낼 유저 정보 >> ", data);
  await api.put(`/user-service/login`, data).then(success).catch(fail);
}

export { signup, login };
