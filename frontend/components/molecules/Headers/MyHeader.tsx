import DeleteUserComponent from "@/components/organisms/DeleteUserComponent/DeleteUserComponent";
import { RootState } from "@/store";
import Link from "next/link";
import { useSelector } from "react-redux";

const MyHeader = () => {
  const isLogin = useSelector((state: RootState) => state.user.isLogin);
  console.log("마이페이지 로그인? >> ", isLogin);
  return (
    <div>
      <Link
        href="/"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        뒤로가기
      </Link>
      <h1>마이페이지 헤더입니다. 뒤로가기, 회원탈퇴 버튼이 있습니다.</h1>
      <DeleteUserComponent />
      <Link
        href="/my/chat"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        채팅
      </Link>
      <Link
        href="/my/emoji"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        나의 이모지
      </Link>
    </div>
  );
};

export default MyHeader;
