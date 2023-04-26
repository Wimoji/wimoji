"use client";

import { useRouter, usePathname } from "next/navigation";
import { useEffect, useState } from "react";

const LoginWrapper = ({ children }: { children: React.ReactNode }) => {
  const router = useRouter();

  const currentPageName = usePathname().slice(1).split("/")[0]; //현재 페이지명
  // const noNeedLoginPages = ["signup", "signin"]; //비로그인 페이지
  const needLoginPages = ["my"]; //로그인이 필요한 페이지
  const isLoginPage = needLoginPages.includes(currentPageName); //로그인이 필요한 페이지인지 확인
  //isLoginPage
  //true: 현재 로그인이 필요한 페이지
  //false: 현재 로그인이 불필요한 페이지

  useEffect(() => {
    const token = sessionStorage.getItem("access-token");

    //로그인이 필요한 페이지에 로그인을 하지 않았을 경우
    if (isLoginPage && token == null) {
      alert("로그인을 해주세요.");
      router.push("/signin");
    }
    //로그인이 필요 없는 페이지에 로그인을 했을 경우
    if (
      (currentPageName === "signin" || currentPageName === "signup") &&
      token != null
    ) {
      router.push("/");
    }
  }, [router]);

  //권한에 따른 페이지는 빈 화면으로 보여줌 >> 추후 로딩 페이지 추가
  // if (
  //   (isLoginPage && token == null) ||
  //   ((currentPageName === "signin" || currentPageName === "signup") &&
  //     token != null)
  // ) {
  //   return <div></div>;
  // }

  return <>{children}</>;
};

export default LoginWrapper;
