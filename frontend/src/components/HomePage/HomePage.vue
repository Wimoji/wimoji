<template>
  <v-container>
    <home-page-create-emoji />
    <home-emoji></home-emoji>
  </v-container>
</template>

<script>
import HomeEmoji from "./HomeEmoji.vue";
import HomePageCreateEmoji from "./HomePageCreateEmoji.vue";
export default {
  components: { HomePageCreateEmoji, HomeEmoji },
};
</script>
<style></style>
