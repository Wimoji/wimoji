<template>
  <v-sheet color="var(--col-empty)">
    <info-page v-if="!isLogin"></info-page>
    <home-page v-else></home-page>
  </v-sheet>
</template>

<script>
import InfoPage from "@/components/InfoPage/InfoPage.vue";
import HomePage from "@/components/HomePage/HomePage.vue";

import { mapState } from "vuex";

export default {
  components: { InfoPage, HomePage },
  computed: {
    ...mapState("userStore", ["isLogin"]),
  },
};
</script>

<style></style>
