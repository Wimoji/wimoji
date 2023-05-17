import { makeChatRoom } from "@/lib/modules/chat";
import { makeEmoji } from "@/lib/modules/emoji";
import { useState } from "react";

interface Props {
  location: {
    latitude: string;
    longitude: string;
    dongCode: string;
    address: string;
  };
}

interface EmojiReq {
  eid: number;
  rid: string;
  title: string;
  latitude: string;
  longitude: string;
  dongCode: string;
  limit: number;
}

const CreateEmoji: React.FC<Props> = ({ location }) => {
  const [isMakeEmoji, setIsMakeEmoji] = useState<boolean>(false);
  const [emoji, setEmoji] = useState<EmojiReq>({
    eid: 1,
    title: "",
    latitude: location.latitude,
    longitude: location.longitude,
    dongCode: location.dongCode,
    limit: 2,
    rid: "",
  });

  const handleIsMakeEmoji = () => {
    setIsMakeEmoji(true);
  };

  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = event.target;
    setEmoji({ ...emoji, [name]: value });
  };

  const handleCreateEmoji = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log("이모지만들기", emoji);
    //1) 채팅방 만들기 요청
    const chatRoomReq = {
      eid: emoji.eid,
      title: emoji.title,
      limit: emoji.limit,
    };
    let chatResult = null;
    await makeChatRoom(chatRoomReq)
      .then((res) => {
        console.log("이모지 생성 요청 - 채팅방", res);
        if (res.data.success) {
          chatResult = res.data.data.rid;
        }
      })
      .catch((err) => {
        console.log(err);
        alert("🤯 에러 발생! 다시 시도해주세요");
      });

    if (chatResult != null && chatResult != undefined) {
      //2) 이모지 생성 요청
      const emojiSaveReq = {
        eid: emoji.eid.toString(),
        rid: chatResult,
        title: emoji.title,
        latitude: location.latitude,
        longitude: location.longitude,
        dongCode: location.dongCode,
      };
      let emojiResult = null;
      await makeEmoji(emojiSaveReq)
        .then((res) => {
          console.log("이모지 생성 요청 - 이모지", res);
          if (res.data.success) {
            alert("이모지 생성 완료! 🧚‍♀️");
          }
        })
        .catch((err) => {
          console.log(err);
          alert("🤯 에러 발생! 다시 시도해주세요");
        });
    }

    //이모지 만들기 창 닫기
    setIsMakeEmoji(false);
    window.location.reload();
  };
  return (
    <div className="bg-orange-200">
      <h1>이모지 만들기 화면</h1>
      <button className="bg-red-500" onClick={handleIsMakeEmoji}>
        이모지 만들기 버튼
      </button>
      {isMakeEmoji && (
        <div>
          <h1>이모지 만들기 내용</h1>
          <form onSubmit={handleCreateEmoji}>
            <div>
              <select id="eid" name="eid" required onChange={handleInputChange}>
                {Array.from({ length: 15 }, (_, i) => i + 1).map((num) => (
                  <option key={num} value={num}>
                    {num}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <input
                type="text"
                id="title"
                name="title"
                required
                placeholder="내용을 입력해주세요..."
                onChange={handleInputChange}
              />
            </div>
            <div>
              <select
                id="limit"
                name="limit"
                required
                placeholder="인원수 선택"
                onChange={handleInputChange}
              >
                {Array.from({ length: 29 }, (_, i) => i + 2).map((num) => (
                  <option key={num} value={num}>
                    {num}
                  </option>
                ))}
              </select>
            </div>

            <button type="submit" className="bg-blue-500">
              생성하기
            </button>
            <button
              type="button"
              onClick={() => setIsMakeEmoji(false)}
              className="bg-red-100"
            >
              닫기
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default CreateEmoji;
