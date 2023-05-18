import SignupForm from "@/components/molecules/SignupForm/SignupForm";
import { signUp } from "@/lib/modules/user";
import { useRouter } from "next/router";
import { useState } from "react";

const SignupComponent = () => {
  const router = useRouter();

  const [nickname, setNickname] = useState("");
  const [uid, setUid] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // 아이디 유효성 검사
    const isAlphanumeric = (str: string) => /^[a-z0-9]+$/i.test(str);
    if (!isAlphanumeric(uid)) {
      alert("아이디는 영어와 숫자로만 입력해주세요.");
      return;
    }

    // 비밀번호 유효성 검사
    const isPasswordValid = (str: string) =>
      /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$/.test(str);
    if (!isPasswordValid(password)) {
      alert("비밀번호는 8~16자 이내의 영어와 숫자 조합으로 입력해주세요.");
      return;
    }

    try {
      const result = await signUp({ nickname, uid, password });
      if (result.data.success) {
        alert(`회원가입이 완료되었습니다. 반가워요 ${nickname}님 😁`);
        //로그인 화면으로 이동
        router.push("/signin");
      }
    } catch (error: any) {
      alert(error.response.data.message);
    }
  };

  const handleNicknameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(event.target.value);
  };

  const handleUidChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUid(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <div>
      <h1>회원가입 컴포넌트</h1>
      <SignupForm
        nickname={nickname}
        uid={uid}
        password={password}
        onSubmit={handleSubmit}
        onNicknameChange={handleNicknameChange}
        onUidChange={handleUidChange}
        onPasswordChange={handlePasswordChange}
      />
    </div>
  );
};

export default SignupComponent;
