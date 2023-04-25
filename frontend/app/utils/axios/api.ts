import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";

interface CustomAxiosInstance extends AxiosInstance {
  (config: AxiosRequestConfig): Promise<AxiosResponse<any>>;
}

const instance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL,
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

export const signUp = (userData: SignupUser) => {
  return instance.post(`/user-service/signup`, userData);
};

export const signIn = (userData: SigninUser) => {
  return instance.put(`/user-service/login`, userData);
};

export const logout = () => {
  return instance.put(`/user-service/logout`);
};

export const deleteUser = () => {
  return instance.delete(`/user-service/`);
};
