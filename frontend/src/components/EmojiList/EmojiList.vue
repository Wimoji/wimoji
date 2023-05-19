<template>
  <v-chip-group column>
    <v-chip
      v-for="(item, i) in emojiCategory"
      :key="i"
      @click="selectEmoji(item.id)"
    >
      <v-avatar left>
        <v-img :src="item.link"> </v-img>
      </v-avatar>
      {{ item.title }}
    </v-chip>
  </v-chip-group>
</template>

<script>
// 15개의 카테고리 이모지
import { mapState } from "vuex";

export default {
  methods: {
    selectEmoji(id) {
      this.$emit("changeEmoji", id);
    },
  },
  data() {
    return {};
  },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
};
</script>

<style></style>
