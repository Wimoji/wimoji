import AroundEmojis from "@/components/molecules/HomeEmoji/AroundEmojis";
import CreateEmoji from "@/components/molecules/HomeEmoji/CreateEmoji";
import useGeolocation from "@/components/molecules/HomeEmoji/useGeolocation";
import { useEffect, useState } from "react";
import { getAddressFromKakao } from "@/lib/kakao";

interface Location {
  latitude: string;
  longitude: string;
  dongCode: string;
  address: string;
}

const HomeComponent = () => {
  //마운트 될 때마다, 현재 위치 정보 가져오기 (geolocation, kakao local api)
  const [location, setLocation] = useState<Location>({
    latitude: "",
    longitude: "",
    dongCode: "",
    address: "",
  });
  const geolocation = useGeolocation();
  console.log("가져온 위치 값", geolocation);

  useEffect(() => {
    if (geolocation.loaded) {
      //카카오 위치 요청
      const params = {
        x: geolocation.coordinates?.lng,
        y: geolocation.coordinates?.lat,
      };
      getAddressFromKakao(params)
        .then((res) => {
          if (res.status == 200) {
            //정상 처리 확인 후 B 위치 저장 -> res 값 확인 후 처리
            const temp = res.data.documents.filter(
              (item: any) => item.region_type == "B"
            )[0];
            const newLocation: Location = {
              latitude: temp.y.toString(),
              longitude: temp.x.toString(),
              dongCode: temp.code,
              address: temp.address_name,
            };
            setLocation(newLocation);
          }
        })
        .catch((err) => console.log(err));
    }
  }, [geolocation]);

  return (
    <div className="bg-slate-100">
      {location?.latitude}
      <br />
      {location?.longitude}
      <br />
      {location?.dongCode}
      <br />
      {location?.address}
      <br />
      <h1>로그인 한 메인화면</h1>
      <h1>주변 이모지 화면</h1>
      <AroundEmojis location={location} />
      {/* <h1>이모지 생성하기 화면</h1> */}
      <CreateEmoji location={location} />
      <button className="bg-blue-300">새로고침 버튼</button>
    </div>
  );
};

export default HomeComponent;
