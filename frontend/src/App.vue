<template>
  <v-app>
    <div class="resize-area" v-if="!isMobile">
      <div class="resize-blue-circle">
        <blue-circle></blue-circle>
      </div>
      <div class="resize-yellow-circle">
        <yellow-circle></yellow-circle>
      </div>
      <div class="resize-white-circle">
        <white-circle :propsText="resizeText"></white-circle>
      </div>
    </div>
    <div v-else>
      <!-- Header -->
      <header-view></header-view>
      <!-- Main -->
      <v-main>
        <transition name="moveInUp">
          <router-view />
        </transition>
      </v-main>
    </div>
  </v-app>
</template>

<script>
import HeaderView from "@/views/HeaderView.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";
import WhiteCircle from "./common/component/WhiteCircle.vue";

export default {
  components: {
    HeaderView,
    BlueCircle,
    YellowCircle,
    WhiteCircle,
  },
  name: "App",
  data: () => ({
    isMobile: false,
    resizeText: [
      "Wimoji는 모바일 환경에 최적화되어있어요!",
      "화면 사이즈를 줄여주세요! 👩‍🏭",
    ],
  }),
  mounted() {
    window.addEventListener("resize", this.checkScreenSize);
    this.checkScreenSize();
  },
  destroyed() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
  methods: {
    checkScreenSize() {
      this.isMobile = window.innerWidth < 500;
    },
  },
};
</script>

<style>
@import "@/assets/styles/font.css";
@import "@/assets/styles/text.css";
@import "@/assets/styles/variable.css";
@import "@/assets/styles/override.css";
@import "@/assets/styles/input.css";

html body {
  background: #fafafa;
  margin: 0 auto;
  width: 100%;
  height: 100%;
}
#app {
  background-color: var(--main-col-1);
  width: 100%;
  height: 100%;
  font-family: var(--main-font-1);
  position: fixed;
  overflow: hidden;
}
/* resize */
.resize-area {
  position: relative;
  width: 100%;
  height: 100%;
}
.resize-blue-circle {
  position: absolute;
  top: 0;
  left: 0;
  transform: translate(50%, 50%);
}
.resize-yellow-circle {
  position: absolute;
  bottom: 0;
  right: 0;
  transform: translate(50%, 50%);
}
.resize-white-circle {
  position: absolute;
  top: 50%;
  left: 50%;
}
/* transition */
.moveInUp-enter-active {
  opacity: 0;
  transition: opacity 0.3s ease-in;
}
.moveInUp-enter-active {
  animation: fadeIn 0.3s ease-in;
}
@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}

@keyframes rotate1 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

@keyframes rotate2 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(-360deg);
  }
}
</style>
