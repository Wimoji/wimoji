import { getUserChatRooms } from "@/lib/modules/user";
import { RootState } from "@/store";
import { changeNowChatRoomState } from "@/store/UserSlice";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

interface ChatRoomRes {
  id: string;
  rid: string;
  eid: number;
  title: string;
  participant: number;
  limit: number;
  isNew: boolean;
}

const ChatListComponent = () => {
  console.log("마운트");
  const router = useRouter();
  const dispatch = useDispatch();

  const [chatList, setChatList] = useState<ChatRoomRes[] | null>(null);

  useEffect(() => {
    getUserChatRooms()
      .then((res) => {
        console.log("유저 채팅 목록 조회", res);
        if (res.data.success) {
          setChatList(res.data.data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const handleClick = (item: ChatRoomRes) => {
    console.log("선택된 채팅");
    dispatch(changeNowChatRoomState(item));
    //새로고침 방지를 위해 세션 스토리지에 저장
    sessionStorage.setItem("nowChatRoom", JSON.stringify(item));
    router.push(`/my/chat/${item.rid}`);
  };

  return (
    <div>
      {chatList == null ? (
        <div>참여한 채팅이 없어요 😂</div>
      ) : (
        <>
          {chatList.map((item, index) => (
            <div key={index} className="flex" onClick={() => handleClick(item)}>
              <div>{item.eid}</div>
              <div>{item.title}</div>
            </div>
          ))}
        </>
      )}
    </div>
  );
};

export default ChatListComponent;
