"use client";

import React, { useState } from "react";
import SigninForm from "../components/Signin/SigninForm";
import { signIn } from "../utils/axios/api";

const Signin: React.FC = () => {
  const [uid, setUid] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await signIn({ uid, password });
      if (response.data.success) {
        alert("로그인 성공");
        //세션 스토리지에 토큰 저장
        sessionStorage.setItem("access-token", response.data.data.accessToken);
        sessionStorage.setItem(
          "refresh-token",
          response.data.data.refreshToken
        );
      }
    } catch (error: any) {
      alert(error.response.data.message);
    }
  };

  const handleUidChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUid(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <div>
      <h1>로그인 페이지</h1>
      <SigninForm
        uid={uid}
        password={password}
        onSubmit={handleSubmit}
        onUidChange={handleUidChange}
        onPasswordChange={handlePasswordChange}
      />
    </div>
  );
};

export default Signin;
