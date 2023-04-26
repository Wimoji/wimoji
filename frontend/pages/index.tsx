import LogoutComponent from "@/components/organisms/LogoutComponent/LogoutComponent";
import Link from "next/link";

export default function Home() {
  return (
    <div>
      <div>로그인 안 한 홈화면...</div>
      <Link href="/signin">지금 시작하기</Link>

      {/* 임시로 로그아웃 버튼을 이곳에 위치시킵니다. */}
      <LogoutComponent />
    </div>
  );
}
