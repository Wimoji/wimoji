export default function Home() {
  const token = sessionStorage.getItem("access-token");
  const isLogin = Boolean(token);

  return (
    <div>
      <span>로그인 안 한 홈화면...</span>
      <span>page.tsx</span>
    </div>
  );
}
