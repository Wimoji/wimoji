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
      class="white-col-1 info-item-component-btn"
      to="/login"
    >
      지금 시작하기
    </v-btn>
    <!-- </div> -->
    <h3 class="info-title-top">같이 해요!</h3>
    <h3 class="info-title-bottom">이모지를 만들고 참여해요😚</h3>
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
.info-title-top {
  position: fixed;
  top: 10%;
  right: 10%;
  z-index: 2;
}
.info-title-bottom {
  position: fixed;
  bottom: 10%;
  left: 10%;
  z-index: 2;
}
.info-item-component {
  position: relative;
  top: 50%;
  left: 50%;
  /* max-width: 500px; */
  transform: translate(-50%, -50%);
  z-index: 1;
}
.info-item-component-btn {
  position: relative;
  top: 40%;
  left: 50%;
  /* max-width: 500px; */
  transform: translate(-50%, -50%);
  z-index: 1;
}
</style>
