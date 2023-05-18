import { getLastReadIdx } from "@/lib/modules/chat";
import { RootState } from "@/store";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

import SockJS from "sockjs-client";
import StompJS, { Stomp } from "@stomp/stompjs";
import { useRouter } from "next/router";

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
  const router = useRouter();

  const [chatRoom, setChatRoom] = useState<ChatRoomRes | null>(null);
  const nowChatRoom = useSelector((state: RootState) => state.user.nowChatRoom);

  const [lastActiveTime, setLastActiveTime] = useState<number | null>(null);
  const [lastReadIdx, setLastReadIdx] = useState<number | null>(null);
  // const [socket, setSocket] = useState(null);
  let socket: any;
  const [mySessionId, setMySessionId] = useState(null);
  const [messages, setMessages] = useState<Object[]>([]);

  useEffect(() => {
    const storedChatRoom = sessionStorage.getItem("nowChatRoom");
    if (storedChatRoom) {
    }
  });

  // useEffect(() => {
  //   //현재 채팅방 정보 설정
  //   if (nowChatRoom.rid.length == 0 && nowChatRoom.limit == 0) {
  //     setChatRoom(JSON.parse(sessionStorage.getItem("nowChatRoom")!));
  //   } else {
  //     setChatRoom(nowChatRoom);
  //   }
  //   console.log("지금 챗방 정보", chatRoom);

  // setThisChatRoom();

  // console.log("현재 채팅방 정보 설정", chatRoom);
  // if (chatRoom == null) return;

  //마지막 활동 시간 측정
  // setLastActiveTime(Date.now());
  // setInterval(() => {
  //   checkUserActivity();
  // }, 300000);

  // //마지막 메시지 조회
  // getLastReadIndex();
  // if (lastReadIdx == null) {
  //   alert("마지막 메시지 조회 실패");
  // } else {
  //   connectSocket();
  // }
  // }, []);

  const connectSocket = () => {
    const sockJs = new SockJS(
      `${process.env.NEXT_PUBLIC_BASE_URL}/chat-service/ws/chat`
    );
    socket = Stomp.over(sockJs);
    // socket = StompJS.over(sockJs);

    let token = "Bearer " + sessionStorage.getItem("access-token");

    if (chatRoom == null) return;
    const headers = {
      Authorization: token,
      rid: chatRoom.id,
    };

    socket.connect(
      headers,
      (frame: any) => {
        console.log("소켓 연결 성공", frame);

        // const url = sockJs._transport.url;
        // const regex = /\/(\w+)\/websocket$/;
        // const sessionId = url.match(regex)[1];
        // setMySessionId(sessionId);

        socket.send(
          "/pub/chat/enter",
          { Authorization: token },
          JSON.stringify(chatRoom.id)
        );

        socket.subscribe(`/sub/chat/${chatRoom.id}`, (msg: any) => {
          //시간 수정
          let nowMsg = JSON.parse(msg.body);
          nowMsg.mtime = nowMsg.mtime.split("T")[1].substring(0, 5);
          // this.messages.push(nowMsg);
          setMessages((messages) => [...messages, nowMsg]);
        }),
          (error: any) => {
            console.log("메시지 수신 실패 ", error);
          };
      },
      (error: any) => {
        console.log("소켓 연결 실패 ", error);
        // rid 오류, uid 오류, 인원 최대
        if (error.command === "ERROR") {
          alert("채팅방에 접속할 수 없습니다.");
          router.push("/my/chat");
        }
      }
    );
  };

  async function setThisChatRoom() {
    if (chatRoom != null) return;
    const storageChatRoom = await JSON.parse(
      sessionStorage.getItem("nowChatRoom")!
    );
    setChatRoom(storageChatRoom);
  }

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
      <div>채팅 보일곳</div>
      <div>
        <form>
          <input type="text" placeholder="내용을 입력해주세요..." />
          <button>보내기</button>
        </form>
      </div>
    </>
  );
};

export default ChatRoomComponent;
