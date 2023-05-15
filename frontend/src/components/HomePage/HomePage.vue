<template>
  <v-sheet color="var(--col-empty)" class="home-area">
    <div class="home-blue-circle">
      <blue-circle></blue-circle>
    </div>
    <div class="home-yellow-circle">
      <yellow-circle></yellow-circle>
    </div>
    <div class="home-center-area">
      <home-white-circle :locText="locText"></home-white-circle>
    </div>
  </v-sheet>
</template>

<script>
import { getAroundEmojis, getEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
import { getNowPosition } from "@/api/modules/location";

import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import HomeWhiteCircle from "./HomeWhiteCircle.vue";
export default {
  components: {
    BlueCircle,
    YellowCircle,
    HomeWhiteCircle,
  },
  computed: {
    ...mapState("userStore", ["user", "location"]),
    ...mapState("emojiStore", ["emojiCategory", "aroundEmojis"]),
  },
  data() {
    return {
      isClickEmoji: false,
      selectedEmoji: null,
      loc: {
        latitude: null,
        longitude: null,
        dongCode: null,
        address: null,
      },
      locText: ["위치 권한을 허용해주세요 📍"],
    };
  },
  async mounted() {
    //1 geolocation으로 현재 위치 설정
    if (navigator.geolocation) {
      // 현재 위치 await
      const getGeolocation = function () {
        return new Promise(function (resolve, reject) {
          navigator.geolocation.getCurrentPosition(resolve, reject);
        });
      };

      //2 현재 gps 설정
      const pos = await getGeolocation();

      //3 지금 위도 경도 설정
      this.loc.latitude = pos.coords.latitude;
      this.loc.longitude = pos.coords.longitude;

      //4 카카오 API로 동코드, 주소 정보 얻어오기
      let params = {
        longitude: this.loc.longitude,
        latitude: this.loc.latitude,
      };
      await getNowPosition(
        params,
        ({ data }) => {
          const temp = data.documents.filter(
            (item) => item.region_type == "B"
          )[0];

          //5 동코드, 주소 저장
          this.loc.dongCode = temp.code;
          this.loc.address = temp.address_name;
        },
        (error) => {
          console.log(error);
        }
      );
      //6 위치 텍스트 설정
      if (this.loc.address != null) {
        this.locText = [
          `${this.user.nickname}님 안녕하세요😆`,
          `지금 ${this.loc.address}에 있어요`,
        ];
      }
      //7 주변 이모지 불러오기
      let data = {
        latitude: `${this.loc.latitude}`,
        longitude: `${this.loc.longitude}`,
        dongCode: this.loc.dongCode,
      };
      let resultAround = await getAroundEmojis(data);

      //8 내 이모지 불러오기
      let resultMyEmoji = await getEmojis();

      //9 전체 이모지 합치기
      this.setAroundEmojis(resultAround);
      this.addMyEmojisToAroundEmojis(resultMyEmoji);
    } else {
      alert("현재 브라우저에서 geolocation을 지원하지 않습니다.");
    }
  },
  methods: {
    ...mapActions("userStore", ["setLocation"]),
    ...mapActions("chatStore", ["setNowChatRoom"]),
    ...mapActions("emojiStore", [
      "setAroundEmojis",
      "addMyEmojisToAroundEmojis",
    ]),
  },
};
</script>
<style>
.home-area .home-center-area {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
}
.create-emoji {
  position: fixed;
  top: 0;

  z-index: 100;
}
/* .info-area .resize-white-circle {
  position: absolute;
  top: 50%;
} */
/* .home-area {
  position: relative;
} */
/* .home-area .create-emoji {
  position: relative;
  width: 100%;
  height: 100%;
} */
</style>
