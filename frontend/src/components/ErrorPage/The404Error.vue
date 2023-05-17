<template>
  <v-sheet class="info-area home-area" color="var(--col-empty)">
    <div class="home-blue-circle">
      <blue-circle></blue-circle>
    </div>
    <div class="home-yellow-circle">
      <yellow-circle></yellow-circle>
    </div>
    <div class="resize-white-circle">
      <white-circle :propsText="notFouondText"></white-circle>
    </div>
    <div class="not-found-area">
      <router-link to="/">메인화면으로 돌아가기</router-link>
    </div>
  </v-sheet>
</template>

<script>
import BlueCircle from "@/common/component/BlueCircle.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import WhiteCircle from "@/common/component/WhiteCircle.vue";
export default {
  components: { BlueCircle, YellowCircle, WhiteCircle },
  data() {
    return {
      notFouondText: ["페이지를 찾을 수 없습니다."],
    };
  },
};
</script>

<style>
.not-found-area {
  text-align: center;
  position: absolute;
  width: 100%;
  bottom: 30%;
  transform: translateY(-50%);
}
</style>
