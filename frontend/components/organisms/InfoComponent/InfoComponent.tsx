import Link from "next/link";

const InfoComponent = () => {
  return (
    <div>
      <hr />
      <h1>로그안 안 한 인포 페이지 입니닷!</h1>
      <h2>이모지를 만들어봐요</h2>
      <h2>친구를 만들어요</h2>
      <Link
        href="/signin"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        지금 시작하기
      </Link>
      <hr />
    </div>
  );
};

export default InfoComponent;
