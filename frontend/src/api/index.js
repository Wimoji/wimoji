import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    // baseURL: `${process.env.VUE_APP_API_BASE_URL}`,
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
  });

  instance.interceptors.request.use(function (config) {
    if (config.url.includes("kakao")) {
      config.headers["Authorization"] =
        "KakaoAK " + process.env.VUE_APP_KAKAOMAP_API_KEY;
      return config;
    }

    const token = sessionStorage.getItem("access-token");
    // const refreshToken = sessionStorage.getItem("refresh-token");

    // access 토큰이 sessionStorage 있으면 header에 포함시켜 전송
    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }

    // refresh 토큰
    // if (refreshToken) {
    //   config.headers["Refresh"] = "Refresh " + refreshToken;
    // }

    console.log("config>>>>>>", config);
    return config;
  });

  instance.interceptors.response.use(function (config) {
    if (config.config.url.includes("kakao")) {
      return config;
    }

    // 새로 발급된 access-token이 있다면 로컬 스토리지에 저장
    if (config.headers["access-token"]) {
      sessionStorage.setItem("access-token", config.headers["access-token"]);
    }

    return config;
  });

  return instance;
}

export { apiInstance };
