import { RootState } from "@/store";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

interface ChatRoomRes {
  id?: string;
  rid?: string;
  eid?: number;
  title?: string;
  participant?: number;
  limit?: number;
  isNew?: boolean;
}
const ChatHeader = () => {
  var nowChatRoom = useSelector((state: RootState) => state.user.nowChatRoom);

  const [chatRoom, setChatRoom] = useState<ChatRoomRes>();

  useEffect(() => {
    console.log("지금 챗룸 정보", nowChatRoom);
    if (nowChatRoom.rid.length == 0 && nowChatRoom.limit == 0) {
      //새로고침시 데이터 날아가는 것 방지
      setChatRoom(JSON.parse(sessionStorage.getItem("nowChatRoom")!));
    } else {
      setChatRoom(nowChatRoom);
    }
  }, []);
  return (
    <div className="flex">
      <Link
        href="/my/chat"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
      >
        뒤로가기
      </Link>
      {chatRoom == null ? (
        <></>
      ) : (
        <div>
          <div>{chatRoom.eid}</div>
          <div>{chatRoom.title}</div>
        </div>
      )}
      <div>
        <button>삭제버튼</button>
      </div>
    </div>
  );
};

export default ChatHeader;
