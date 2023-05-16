import LogoutComponent from "@/components/organisms/LogoutComponent/LogoutComponent";
// import { RootState } from "@/store";
import Link from "next/link";
// import { useSelector } from "react-redux";

const HomeHeader = () => {
  // const isLogin = useSelector((state: RootState) => state.user.isLogin);
  // console.log("홈헿더 로그인? >> ", isLogin);

  return (
    <div className="bg-indigo-50">
      <h1>홈 헤더</h1>
      <Link
        href="/my"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        마이페이지
      </Link>
      <LogoutComponent />
    </div>
  );
};

export default HomeHeader;
