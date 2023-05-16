import AroundEmojis from "@/components/molecules/HomeEmoji/AroundEmojis";
import CreateEmoji from "@/components/molecules/HomeEmoji/CreateEmoji";
import useGeolocation from "@/components/molecules/HomeEmoji/useGeolocation";
import { kakaoApi } from "@/lib/emoji";
import { useEffect, useState } from "react";

interface Location {
  lat: number;
  lon: number;
}

interface HomeReq {
  latitude: string;
  longitude: string;
  dongCode: string;
}

const HomeComponent = () => {
  var { latitude, longitude, error } = useGeolocation();

  if (error) {
    console.log("geolocation error", error);
  }
  console.log("!!", latitude, longitude);

  //카카오 지도 요청
  const [location, setLocation] = useState<Location>({
    lat: latitude,
    lon: longitude,
  });
  console.log("다시 설정한 로케이션", location);

  if (location.lat != 0 && location.lon != 0) {
    console.log("카카오요청가야돼");
    try {
      const result = kakaoApi({
        x: location.lon,
        y: location.lat,
      });
      console.log("kakao result>>", result);
    } catch (error) {
      console.log(error);
    }
  }
  // useEffect(() => {
  //   console.log("호출", location);
  //   if (location.longitude != 0 && location.latitude != 0) {
  //     console.log("카카오요청가야돼");
  //     try {
  //       const result = kakaoApi({
  //         x: location.longitude,
  //         y: location.latitude,
  //       });
  //       console.log("kakao result>>", result);
  //     } catch (error) {
  //       console.log(error);
  //     }
  //   }
  // }, [location]);

  return (
    <div className="bg-slate-100">
      <h1>로그인 한 메인화면</h1>
      <h1>주변 이모지 화면</h1>
      {/* <AroundEmojis /> */}
      {/* <h1>이모지 생성하기 화면</h1> */}
      <CreateEmoji />
      <button className="bg-blue-300">새로고침 버튼</button>
    </div>
  );
};

export default HomeComponent;
