import Link from "next/link";

const ChatHeader = () => {
  return (
    <div>
      <h1>채팅방 헤더입니다. 뒤로가기 버튼만 있습니다</h1>
      <Link
        href="/my/chat"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        뒤로가기
      </Link>
    </div>
  );
};

export default ChatHeader;
