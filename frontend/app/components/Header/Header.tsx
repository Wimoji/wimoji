import Link from "next/link";
import { MdOutlineArrowBackIos } from "react-icons/md";
import LogoutButton from "../Button/LogoutButton";
import { deleteUser, logout } from "@/app/utils/axios/api";
import { useRouter } from "next/navigation";
import DeleteUserButton from "../Button/DeleteUserButton";

type HeaderProps = {
  back?: boolean;
  children?: React.ReactNode;
};

export default function Header({ back, children }: HeaderProps) {
  const router = useRouter();

  //로그아웃
  const hadleLogout = async () => {
    try {
      const result = await logout();
      if (result.data.success) {
        alert("로그아웃 되었습니다.");
      }
    } catch (error: any) {
      alert(error.response.data.message);
    }
    //세션 스토리지에서 token 삭제
    sessionStorage.clear();
    //페이지 이동
    router.refresh();
  };

  //회원탈퇴
  const handleDeleteUser = async () => {
    try {
      if (confirm("탈퇴하시나요? ＼（〇_ｏ）／")) {
        const result = await deleteUser();
        if (result.data.success) {
          alert("탈퇴가 완료되었습니다. /(ㄒoㄒ)/~~");
          //세션 스토리지에서 token 삭제
          sessionStorage.clear();
        }
      }
    } catch (error: any) {
      alert(error.response.data.message);
    }
    //페이지 이동
    router.push("/");
  };

  return (
    <header>
      <nav className="flex sm:justify-center space-x-4 bg-sky-400">
        {back && (
          <Link href="/">
            <MdOutlineArrowBackIos />
          </Link>
        )}
        <Link href="/my/chat">채팅</Link>
        {back || <LogoutButton onClick={hadleLogout} />}
        {back && <Link href="/my/emoji">나의 이모지</Link>}
        {back && <DeleteUserButton onClick={handleDeleteUser} />}
        {children}
      </nav>
    </header>
  );
}
