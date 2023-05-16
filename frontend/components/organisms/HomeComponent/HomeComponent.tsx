import AroundEmojis from "@/components/molecules/HomeEmoji/AroundEmojis";
import CreateEmoji from "@/components/molecules/HomeEmoji/CreateEmoji";
import { getAddressFromKakao } from "@/lib/modules/kakao";
import { useEffect, useState } from "react";

const HomeComponent = () => {
  //마운트 될 때마다, 현재 위치 정보 가져오기 (geolocation, kakao local api)
  const [location, setLocation] = useState({
    latitude: "",
    longitude: "",
    dongCode: "",
    address: "",
  });

  useEffect(() => {
    //1) geolocation
    const { geolocation } = navigator;

    geolocation.getCurrentPosition(
      (position) => {
        //success
        //위도 경도 업데이트
        setLocation({
          ...location,
          latitude: position.coords.latitude.toString(),
          longitude: position.coords.longitude.toString(),
        });
      },
      (error) => {
        console.log("Geolocation을 지원하지 않는 브라우저입니다.");
      }
    );

    //2) kakao
    if (location.latitude != "" && location.longitude != "") {
      //위도 경도 값이 존재할 때 kakao local API 호출
      const params = {
        x: location.longitude,
        y: location.latitude,
      };
      getAddressFromKakao(params)
        .then((res) => {
          console.log(res);
          //정상 처리 확인 후 B 위치 저장 -> res 값 확인 후 처리
          // const temp = res.data.documents.filter(
          //   (item: any) => item.region_type == "B"
          // )[0];

          // setLocation({
          //   ...location,
          //   dongCode: temp.code,
          //   address: temp.address_name,
          // });
        })
        .catch((err) => console.log(err));
    }
  }, []);

  return (
    <div className="bg-slate-100">
      <h1>로그인 한 메인화면</h1>
      <h1>주변 이모지 화면</h1>
      <AroundEmojis location={location} />
      {/* <h1>이모지 생성하기 화면</h1> */}
      <CreateEmoji />
      <button className="bg-blue-300">새로고침 버튼</button>
    </div>
  );
};

export default HomeComponent;
