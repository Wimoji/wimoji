import React from "react";

interface SignupFormProps {
  nickname: string;
  uid: string;
  password: string;
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
  onNicknameChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onUidChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onPasswordChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const SignupForm: React.FC<SignupFormProps> = ({
  nickname,
  uid,
  password,
  onSubmit,
  onNicknameChange,
  onUidChange,
  onPasswordChange,
}) => {
  return (
    <form onSubmit={onSubmit}>
      <input
        type="text"
        id="nickname"
        value={nickname}
        onChange={onNicknameChange}
        placeholder="닉네임"
        required
      />
      <input
        type="text"
        id="uid"
        value={uid}
        onChange={onUidChange}
        placeholder="아이디"
        required
      />
      <input
        type="password"
        id="password"
        value={password}
        onChange={onPasswordChange}
        placeholder="비밀번호"
        required
      />
      <button type="submit">회원가입</button>
    </form>
  );
};

export default SignupForm;
