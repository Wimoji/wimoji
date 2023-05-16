import { useState } from "react";

interface GeolocationData {
  latitude: number;
  longitude: number;
  error?: string;
}

interface KakaoLocalData {
  dongCode?: string;
  address?: string;
}

const useKakaoLocal = (location: GeolocationData): KakaoLocalData => {
  const [data, setData] = useState<KakaoLocalData>({
    dongCode: "",
    address: "",
  });
  if (location.latitude == 0 && location.longitude == 0) {
    console.log("geolocation error", location.error);
    return null;
  }

  console.log("props로 넘어옴", location);

  return data;
};

export default useKakaoLocal;
