import React from "react";

interface SigninFormProps {
  uid: string;
  password: string;
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
  onUidChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onPasswordChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const SigninForm: React.FC<SigninFormProps> = ({
  uid,
  password,
  onSubmit,
  onUidChange,
  onPasswordChange,
}) => {
  return (
    <form onSubmit={onSubmit}>
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
      <button type="submit">로그인</button>
    </form>
  );
};

export default SigninForm;
