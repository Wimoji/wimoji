<template>
  <v-sheet class="info-area" color="var(--col-empty)">
    <div class="blue-circle-area blue-info">
      <blue-circle></blue-circle>
    </div>
    <div class="yellow-circle-area yellow-info">
      <yellow-circle></yellow-circle>
    </div>
    <!-- <div class="info-item-component d-flex flex-column align-center"> -->
    <info-item class="info-item-component"></info-item>
    <v-btn
      height="50px"
      width="70%"
      rounded
      color="var(--main-col-3)"
      class="white-col-1 info-item-component"
      to="/login"
    >
      지금 시작하기
    </v-btn>
    <!-- </div> -->
    <div class="info-title-area">서비스를 소개하는 페이지입니다.</div>
  </v-sheet>
</template>

<script>
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import InfoItem from "./InfoItem.vue";

export default {
  components: {
    BlueCircle,
    YellowCircle,
    InfoItem,
  },
  methods: {
    goLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style>
.info-area {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}
.blue-info {
  position: absolute;
  bottom: 50%;
  right: 50%;
  scale: 0.7;
  z-index: 1;
}
.yellow-info {
  position: absolute;
  top: 50%;
  left: 50%;
  scale: 0.8;
  z-index: 1;
}
.info-title-area {
  z-index: 2;
  margin: 0 auto;
}
.info-item-component {
  position: relative;
  top: 50%;
  left: 50%;
  /* max-width: 500px; */
  transform: translate(-50%, -50%);
  z-index: 1;
}
/* .info-item-component a {
  margin: 0 auto;
} */
</style>
