import { getLastReadIdx } from "@/lib/modules/chat";
import { RootState } from "@/store";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

import SockJS from "sockjs-client";
import * as StompJS from "@stomp/stompjs";

interface ChatRoomRes {
  id: string;
  rid: string;
  eid: number;
  title: string;
  participant: number;
  limit: number;
  isNew: boolean;
}

const ChatRoomComponent = () => {
  var nowChatRoom = useSelector((state: RootState) => state.user.nowChatRoom);

  const [chatRoom, setChatRoom] = useState<ChatRoomRes | null>(null);
  const [lastActiveTime, setLastActiveTime] = useState<number | null>(null);
  const [lastReadIdx, setLastReadIdx] = useState<number | null>(null);
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    //현재 채팅방 정보 설정
    if (nowChatRoom.rid.length == 0 && nowChatRoom.limit == 0) {
      setChatRoom(JSON.parse(sessionStorage.getItem("nowChatRoom")!));
    } else {
      setChatRoom(nowChatRoom);
    }

    //마지막 활동 시간 측정
    setLastActiveTime(Date.now());
    setInterval(() => {
      checkUserActivity();
    }, 300000);

    //마지막 메시지 조회
    getLastReadIndex();
    if (lastReadIdx == null) {
      alert("마지막 메시지 조회 실패");
    } else {
      connectSocket();
    }
  }, []);

  const connectSocket = () => {
    const sockJs = new SockJS(
      `${process.env.NEXT_PUBLIC_BASE_URL}/chat-service/ws/chat`
    );
    // setSocket(StompJS.over(sockJs));
  };

  async function getLastReadIndex() {
    if (chatRoom == null) return;
    const lastIdx = await getLastReadIdx(chatRoom.id)
      .then((res) => {
        console.log("마지막 메시지 조회", res);
        if (res.data.success) {
          setLastReadIdx(res.data.data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }

  const checkUserActivity = () => {
    //5분 동안 활동이 없으면 disconnect
    if (lastActiveTime == null) return;
    const timeDiff = Date.now() - lastActiveTime;
    if (timeDiff > 300000) {
      disconnectWebSocket();
    }
  };

  const disconnectWebSocket = () => {
    //나가기 버튼 : 마지막 메시지 저장
    //웹소켓 종료
  };

  return (
    <>
      <h1>채팅들</h1>
      <h1>보내기창</h1>
    </>
  );
};

export default ChatRoomComponent;
