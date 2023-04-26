import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";

interface CustomAxiosInstance extends AxiosInstance {
  (config: AxiosRequestConfig): Promise<AxiosResponse<any>>;
}

const instance = axios.create({
  // baseURL: process.env.NEXT_PUBLIC_BASE_URL,
  timeout: 5000,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
  },
});

interface SignupUser {
  nickname: string;
  uid: string;
  password: string;
}

interface SigninUser {
  uid: string;
  password: string;
}

//Interceptors
instance.interceptors.request.use((config) => {
  //session storage에 access token이 존재한다면 담아서 보낸다
  const token = sessionStorage.getItem("access-token");
  const refreshToken = sessionStorage.getItem("refresh-token");

  // access 토큰이 sessionStorage 있으면 header에 포함시켜 전송
  if (token) {
    config.headers["Authorization"] = "Bearer " + token;
  }

  // refresh 토큰
  if (refreshToken) {
    config.headers["Refresh"] = "Refresh " + refreshToken;
  }

  return config;
});

instance.interceptors.response.use((config) => {
  // 새로 발급된 access-token이 있다면 세션 스토리지에 저장
  if (config.headers["access-token"]) {
    sessionStorage.setItem("access-token", config.headers["access-token"]);
  }
  return config;
});

export const signUp = (userData: SignupUser) => {
  return instance.post(
    `${process.env.NEXT_PUBLIC_USER_SERVICE_URL}/signup`,
    userData
  );
};

export const signIn = (userData: SigninUser) => {
  return instance.put(
    `${process.env.NEXT_PUBLIC_USER_SERVICE_URL}/login`,
    userData
  );
};

export const logout = () => {
  return instance.put(`${process.env.NEXT_PUBLIC_USER_SERVICE_URL}/logout`);
};

export const deleteUser = () => {
  return instance.delete(`${process.env.NEXT_PUBLIC_USER_SERVICE_URL}/`);
};
