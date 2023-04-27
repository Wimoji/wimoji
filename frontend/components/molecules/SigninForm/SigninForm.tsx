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
        className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      />
      <input
        type="password"
        id="password"
        value={password}
        onChange={onPasswordChange}
        placeholder="비밀번호"
        required
        className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      />
      <button
        type="submit"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        로그인
      </button>
    </form>
  );
};

export default SigninForm;
