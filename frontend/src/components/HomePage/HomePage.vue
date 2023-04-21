<template>
  <v-container>
    <home-header></home-header>
    <div>사용자 주변의 이모지가 보이는 홈페이지입니다.</div>
    <div>
      <v-btn
        >이모지 만들기
        <v-icon>mdi-heart-plus-outline</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import HomeHeader from "@/views/Header/HomeHeader.vue";
export default {
  components: { HomeHeader },
};
</script>

<style></style>
