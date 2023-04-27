import InfoComponent from "../components/organisms/InfoComponent/InfoComponent";
import HomeComponent from "@/components/organisms/HomeComponent/HomeComponent";
import { RootState } from "@/store";
import { useSelector } from "react-redux";

export default function Home() {
  const isLogin = useSelector((state: RootState) => state.user.isLogin);

  return <div>{isLogin ? <HomeComponent /> : <InfoComponent />}</div>;
}
