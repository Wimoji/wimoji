import ChatHeader from "@/components/molecules/Headers/ChatHeader";
import HomeHeader from "@/components/molecules/Headers/HomeHeader";
import MyHeader from "@/components/molecules/Headers/MyHeader";
import { useRouter } from "next/router";
import { useState, useEffect } from "react";

const HeaderComponent = () => {
  //현재 경로의 이름을 확인하고 헤더를 다르게 return 합니다

  const [token, setToken] = useState<null | string>(null);

  useEffect(() => {
    const accessToken = sessionStorage.getItem("access-token");
    setToken(accessToken);
  });

  const pathname = useRouter().pathname;

  if (pathname === "/" && token != null) {
    return <HomeHeader />;
  }

  if (pathname.startsWith("/my")) {
    if (pathname.includes("/chat/")) {
      return <ChatHeader />;
    } else {
      return <MyHeader />;
    }
  }

  return null;
};

export default HeaderComponent;
