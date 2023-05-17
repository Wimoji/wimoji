import { getAroundEmojis } from "@/lib/modules/emoji";
import { myChat } from "@/lib/modules/user";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

interface Props {
  location: {
    latitude: string;
    longitude: string;
    dongCode: string;
    address: string;
  };
}

interface HomeRes {
  uid: string;
  eid: string;
  rid: string;
  title: string;
  latitude: string;
  longitude: string;
  dongCode: string;
  dis: number;
  participant: number;
  limit: number;
  isEnter: boolean;
}

const AroundEmojis: React.FC<Props> = ({ location }) => {
  const router = useRouter();
  const [emojis, setEmojis] = useState<HomeRes[]>();
  const [selectedItem, setSelectedItem] = useState<HomeRes | null>(null);

  useEffect(() => {
    if (
      location === undefined ||
      (location.latitude.length == 0 && location.longitude.length == 0)
    ) {
      return;
    }
    //위치가 변경될때마다 주변 이모지 검색
    const homeReq = {
      latitude: location.latitude,
      longitude: location.longitude,
      dongCode: location.dongCode,
    };
    getAroundEmojis(homeReq)
      .then((res) => {
        console.log("주변이모지조회", res);
        //주변 이모지 저장
        if (res.data.success) {
          const result = res.data.data;
          setEmojis(result);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, [location]);

  //이모지 상세 보기 처리
  const handleClick = (item: HomeRes) => {
    console.log(item);
    setSelectedItem(item);
  };

  //함께하기 처리
  const handleJoinChat = async (item: HomeRes) => {
    await myChat(item.rid)
      .then((res) => {
        console.log("함께하기", res);
        router.push("/my/chat");
      })
      .catch((err) => {
        console.log(err);
        alert("채팅방 인원이 마감됐어요 😢");
      });
  };

  return (
    <div className="bg-red-100">
      <h1>주변 이모지 화면</h1>
      {/* 주변 이모지 화면에 표시 */}
      {emojis === undefined ? (
        <h1>주변 이모지 없음</h1>
      ) : (
        <>
          {emojis.map((item, index) => (
            <div
              className="bg-yellow-200"
              key={index}
              onClick={() => handleClick(item)}
            >
              {item.title}
            </div>
          ))}
          {selectedItem && (
            <div>
              <h2 className="text-blue-400">{selectedItem.title} 상세보기 !</h2>
              <button onClick={() => handleJoinChat(selectedItem)}>
                함께하기
              </button>
              <button
                className="bg-indigo-200"
                onClick={() => setSelectedItem(null)}
              >
                닫기
              </button>
              <h3>
                ({selectedItem.participant}/{selectedItem.limit})
              </h3>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default AroundEmojis;
