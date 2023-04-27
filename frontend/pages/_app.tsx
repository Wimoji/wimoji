import HeaderComponent from "@/components/organisms/HeaderComponent/HeaderComponent";
import { wrapper } from "@/store";
import { changeUserState } from "@/store/UserSlice";
import "@/styles/globals.css";
import type { AppProps } from "next/app";
import { useRouter } from "next/router";
import { useEffect } from "react";
import { useDispatch } from "react-redux";

function App({ Component, pageProps }: AppProps) {
  const dispatch = useDispatch();
  const router = useRouter();

  useEffect(() => {
    const token = sessionStorage.getItem("access-token");
    if (token != null) {
      dispatch(changeUserState(true));
    }

    //비로그인 : 로그인 필요 페이지 접근시
    if (router.pathname.startsWith("/my") && token == null) {
      alert(`로그인이 필요합니다.`);
      // alert(`로그인이 필요합니다. 현재 페이지: ${router.pathname}`);
      router.push("/signin");
    }

    //로그인 : 비로그인 페이지 접근시
    if (["/signin", "/signup"].includes(router.pathname) && token != null) {
      // alert(`로그인 했는데 여기 왜 와요? ${router.pathname}`);
      router.push("/");
    }
  }, [router]);
  return (
    <div>
      <HeaderComponent />
      <Component {...pageProps} />
    </div>
  );
}
export default wrapper.withRedux(App);
