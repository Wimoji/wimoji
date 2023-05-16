import { getAroundEmojis } from "@/lib/modules/emoji";
import { useEffect, useState } from "react";

interface Location {
  location: {
    latitude: string;
    longitude: string;
    dongCode: string;
    address: string;
  };
}

const handleClick = (e: any) => {
  //이모지 상세 보기 처리
  console.log(e);
};

const AroundEmojis = (props: Location) => {
  const [emojis, setEmojis] = useState();

  useEffect(() => {
    //위치가 변경될때마다 주변 이모지 검색
    const homeReq = {
      latitude: props.location.latitude,
      longitude: props.location.longitude,
      dongCode: props.location.dongCode,
    };
    getAroundEmojis(homeReq)
      .then((res) => {
        console.log(res);
        //주변 이모지 저장
        // setEmojis();
      })
      .catch((err) => {
        console.log(err);
      });
  }, [props]);

  return (
    <div>
      <h1>주변 이모지 화면</h1>
      {/* 주변 이모지 화면에 표시 */}
      {/* 클릭했을 때 상세 보기 설정 */}
      {/* 예시 */}
      <div onClick={handleClick}>이모지입니다</div>
    </div>
  );
};

export default AroundEmojis;
