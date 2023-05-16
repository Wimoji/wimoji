import { useState, useEffect } from "react";

interface GeolocationData {
  latitude: number;
  longitude: number;
  error?: string;
}

const useGeolocation = (): GeolocationData => {
  const [data, setData] = useState<GeolocationData>({
    latitude: 0,
    longitude: 0,
  });

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setData({
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
          });
        },
        (error) => {
          setData({ latitude: 0, longitude: 0, error: error.message });
        }
      );
    } else {
      setData({
        latitude: 0,
        longitude: 0,
        error: "Geolocation is not supported",
      });
    }
  }, []);

  return data;
};

export default useGeolocation;
