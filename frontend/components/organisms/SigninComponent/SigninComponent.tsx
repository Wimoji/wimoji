import SigninForm from "@/components/molecules/SigninForm/SigninForm";
import { RootState } from "@/store";
import { changeUserState } from "@/store/UserSlice";
import { signIn } from "@/lib/modules/user";
import Link from "next/link";
import { useRouter } from "next/router";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";

const SigninComponent = () => {
  // const dispatch = useDispatch();
  // const isLogin = useSelector((state: RootState) => state.user.isLogin);

  const router = useRouter();

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
        //닉네임 저장
        sessionStorage.setItem("nickname", response.data.data.nickname);

        //메인 화면으로 이동
        router.push("/");
      }
    } catch (error: any) {
      console.log(error);
      if (error.response != null) {
        alert(error.response.data.message);
      } else {
        alert(error.message);
      }
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
      <h1>로그인</h1>
      <SigninForm
        uid={uid}
        password={password}
        onSubmit={handleSubmit}
        onUidChange={handleUidChange}
        onPasswordChange={handlePasswordChange}
      />
      <Link
        href="/signup"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        회원가입 하기
      </Link>
    </div>
  );
};

export default SigninComponent;
