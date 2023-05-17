import { deleteEmoji, getEmojis } from "@/lib/modules/emoji";
import { useEffect, useState } from "react";

interface EmojiGetRes {
  eid: string;
  rid: string;
  title: string;
  latitude: string;
  longitude: string;
  dongCode: string;
  participant: number;
  limit: number;
  isEnter: boolean;
}

interface SelectEmoji {
  emoji: EmojiGetRes;
  index: number;
}

const MyEmojiComponent = () => {
  const [myEmojiList, setMyEmojiList] = useState<EmojiGetRes[] | null>(null);
  const [selectedEmoji, setSelectedEmoji] = useState<SelectEmoji | null>(null);

  useEffect(() => {
    getEmojis()
      .then((res) => {
        console.log("유저 이모지 조회", res);
        if (res.data.success) {
          setMyEmojiList(res.data.data);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const handleDelete = (item: SelectEmoji) => {
    //선택한 이모지 삭제
    console.log("선택한 이모지", item);
    const delData = { order: item.index.toString(), eid: item.emoji.eid };
    deleteEmoji(delData)
      .then((res) => {
        console.log("이모지 삭제 요청", res);
        if (res.data.success) {
          alert("이모지를 삭제했어요! 😮");
          if (myEmojiList != null) {
            setMyEmojiList(
              myEmojiList.filter(
                (emoji) => emoji.rid !== selectedEmoji?.emoji.rid
              )
            );
          }
          setSelectedEmoji(null);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      {myEmojiList == null ? (
        <div>내 이모지가 없어요 😅</div>
      ) : (
        <>
          {myEmojiList.map((item, index) => (
            <div
              onClick={() => setSelectedEmoji({ emoji: item, index: index })}
              key={index}
            >
              <div>{item.eid}</div>
            </div>
          ))}
          {selectedEmoji && (
            <>
              <div>{selectedEmoji.emoji.eid}</div>
              <div>{selectedEmoji.emoji.title}</div>
              <button onClick={() => handleDelete(selectedEmoji)}>삭제</button>
              <button onClick={() => setSelectedEmoji(null)}>닫기</button>
            </>
          )}
        </>
      )}
    </>
  );
};

export default MyEmojiComponent;
