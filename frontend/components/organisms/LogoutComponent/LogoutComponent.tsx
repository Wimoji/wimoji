import LogoutButton from "@/components/atoms/Button/LogoutButton";
import { logout } from "@/lib/user";
import { useRouter } from "next/router";

const LogoutComponent = () => {
  const router = useRouter();

  //로그아웃
  const hadleLogout = async () => {
    //로그인 상태인지 확인 >> 수정 필요
    try {
      const result = await logout();
      if (result.data.success) {
        alert("다음에 또 봐요! 🤗");
      }
    } catch (error: any) {
      alert(error.response.data.message);
    }
    //세션 스토리지에서 token 삭제
    sessionStorage.clear();
    //페이지 새로고침
    router.reload();
  };

  return <LogoutButton onClick={hadleLogout} />;
};

export default LogoutComponent;
