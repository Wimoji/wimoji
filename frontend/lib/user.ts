import api from "./api";

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

interface UserReq {
  accessToken: string;
  refreshToken: string;
  nickname: string;
}

export const signUp = async (userData: SignupUser) => {
  return await api.post(`/user-service/signup`, userData);
};

export const signIn = async (userData: SigninUser) => {
  return await api.put(`/user-service/login`, userData);
};

export const logout = async () => {
  return await api.put(`/user-service/logout`);
};

export const deleteUser = async () => {
  return await api.delete(`/user-service/`);
};
