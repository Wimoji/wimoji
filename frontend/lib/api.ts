import axios from "axios";

const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
  // timeout: 5000,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
  },
});

//Interceptors
api.interceptors.request.use((config) => {
  //session storage에 access token이 존재한다면 담아서 보낸다
  const token = sessionStorage.getItem("access-token");

  // access 토큰이 sessionStorage 있으면 header에 포함시켜 전송
  if (token) {
    config.headers["Authorization"] = "Bearer " + token;
  }
  return config;
});

api.interceptors.response.use((config) => {
  // 새로 발급된 access-token이 있다면 세션 스토리지에 저장
  if (config.headers["access-token"]) {
    sessionStorage.setItem("access-token", config.headers["access-token"]);
  }
  return config;
});

export default api;
