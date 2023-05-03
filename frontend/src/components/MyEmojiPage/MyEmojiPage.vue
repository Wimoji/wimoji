<template>
  <v-container>
    <my-emoji-list />
  </v-container>
</template>

<script>
import { mapState } from "vuex";
import MyEmojiList from "./MyEmojiList.vue";

export default {
  components: { MyEmojiList },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
};
</script>

<style></style>
