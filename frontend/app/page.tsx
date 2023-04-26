"use client";

import Link from "next/link";
import { useEffect, useState } from "react";

export default function Home() {
  const [token, setToken] = useState<null | string>(null);

  useEffect(() => {
    const accessToken = sessionStorage.getItem("access-token");
    setToken(accessToken);
  });

  return (
    <div>
      {token ? (
        <div>
          <div>로그인 한 홈화면...</div>
        </div>
      ) : (
        <div>
          <div>로그인 안 한 홈화면...</div>
          <Link href="/signin">지금 시작하기</Link>
        </div>
      )}
    </div>
  );
}
