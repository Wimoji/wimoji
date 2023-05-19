<template>
  <v-sheet class="info-area home-area" color="var(--col-empty)">
    <div class="home-blue-circle">
      <blue-circle></blue-circle>
    </div>
    <div class="home-yellow-circle">
      <yellow-circle></yellow-circle>
    </div>
    <div class="info-describe">
      <info-item></info-item>
      <v-btn
        height="50px"
        width="70%"
        rounded
        color="var(--main-col-3)"
        class="white-col-1"
        to="/login"
      >
        지금 시작하기
      </v-btn>
    </div>
    <div class="info-text">
      <transition name="moveInUp">
        <h3 v-if="!textFlag" class="info-text-top">{{ infoText1 }}</h3>
      </transition>
      <transition name="moveInUp">
        <h3 v-if="textFlag" class="info-text-top">{{ infoText3 }}</h3>
      </transition>
      <transition name="moveInUp">
        <h3 v-if="!textFlag" class="info-text-bottom">{{ infoText2 }}</h3>
      </transition>
      <transition name="moveInUp">
        <h3 v-if="textFlag" class="info-text-bottom">{{ infoText4 }}</h3>
      </transition>
    </div>
    <the-footer></the-footer>
  </v-sheet>
</template>

<script>
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import InfoItem from "@/components/InfoPage/InfoItem.vue";
import TheFooter from "@/views/TheFooter.vue";

export default {
  components: {
    BlueCircle,
    YellowCircle,
    InfoItem,
    TheFooter,
  },
  data() {
    return {
      textFlag: false,
      infoText1: "같이 해요!",
      infoText2: "이모지를 만들고 참여해요 😚",
      infoText3: "🤸‍♀️ 친구를 만들어봐요",
      infoText4: "wimoji를 이용해보세요! 💌",
    };
  },
  mounted() {
    setInterval(() => {
      this.textFlag = !this.textFlag;
    }, 2500);
  },
  methods: {
    goLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style>
.info-describe {
  text-align: center;
  position: absolute;
  width: 100%;
  top: 50%;
  transform: translateY(-50%);
}
.info-describe a {
  margin-top: 5%;
}
.info-text-top {
  position: fixed;
  top: 15%;
  right: 10%;
}
.info-text-bottom {
  position: fixed;
  bottom: 15%;
  left: 10%;
}
/* transition */
.moveInUp-enter-active {
  opacity: 0;
  transition: opacity 0.5s ease-in;
}
.moveInUp-enter-active {
  animation: fadeIn 0.5s ease-in;
}
@keyframes fadeIn {
  0% {
    opacity: 0;
    -webkit-opacity: 0;
  }
  50% {
    opacity: 0.5;
    -webkit-opacity: 0.5;
  }
  100% {
    opacity: 1;
    -webkit-opacity: 1;
  }
}
</style>
