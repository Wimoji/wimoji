<template>
  <v-app>
    <v-sheet v-if="!isMobile" color="var(--main-col-1)">
      <div class="circle-area">
        <div class="main-blue-circle">
          <blue-circle></blue-circle>
        </div>
        <div class="main-yellow-circle">
          <yellow-circle></yellow-circle>
        </div>
      </div>
      <div class="resize-title-circle"></div>
      <div class="resize-title-circle circle2"></div>
      <h3 class="resize-title">
        Wimoji는 모바일 환경에 최적화되어있어요!<br /><br />화면 사이즈를
        줄여주세요! 👩‍🏭
      </h3>
    </v-sheet>
    <v-sheet v-else color="var(--col-empty)">
      <!-- Header -->
      <header-view></header-view>
      <!-- Main -->
      <v-main>
        <transition name="moveInUp">
          <router-view />
        </transition>
      </v-main>
      <!-- Footer -->
      <router-view name="footer" />
    </v-sheet>
  </v-app>
</template>

<script>
import HeaderView from "@/views/HeaderView.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";

export default {
  components: { HeaderView, BlueCircle, YellowCircle },
  name: "App",
  data: () => ({
    isMobile: false,
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
  /* max-width: 500px; */
  margin: 0 auto;
  height: 100%;
  min-height: 100%;
}
#app {
  background-color: var(--main-col-1);
  max-width: 100%;
  min-height: 100%;
  font-family: var(--main-font-1);
}
.resize-title-circle {
  z-index: 2;
  box-sizing: border-box;

  position: fixed;
  width: 430px;
  height: 420px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  border-radius: 50%;
  animation: rotate1 8s linear infinite;
}
.circle2 {
  width: 410px;
  height: 400px;
  animation: rotate2 4s linear infinite;
  border: 2px solid #ffffff;
  background: rgba(255, 255, 255, 0.31);
}
.resize-title {
  position: absolute;
  width: 100%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  z-index: 3;
}
.circle-area {
  position: fixed;
  top: 0;
  left: 0;
}
.main-blue-circle {
  position: fixed;
  top: 0%;
  left: 0%;
}
.main-yellow-circle {
  position: fixed;
  bottom: 0;
  right: 0;
}
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
