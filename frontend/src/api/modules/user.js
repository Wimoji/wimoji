import { apiInstance } from "@/api/index";

const api = apiInstance();

//[ POST /user-service/signup ] 회원 가입
async function makeUser(data, success, fail) {
  await api.post(`/user-service/signup`, data).then(success).catch(fail);
}

export { makeUser };
