<template>
  <Flicking
    :options="{ circular: true, horizontal: true, iOSEdgeSwipeThreshold: 30 }"
    :plugins="plugins"
  >
    <v-sheet
      color="var(--col-empty)"
      v-for="(item, i) in emojiCategory"
      :key="i"
    >
      <v-img :src="item.link" width="10rem"> </v-img>
    </v-sheet>
  </Flicking>
</template>

<script>
import { Flicking } from "@egjs/vue-flicking";
import { AutoPlay, Perspective } from "@egjs/flicking-plugins";
import { mapState } from "vuex";

export default {
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  components: { Flicking },
  data() {
    return {
      currentIndex: 0,
      timer: null,
      // plugins: [
      //   new AutoPlay({ duration: 2000, direction: "NEXT", stopOnHover: false }),
      // ],
      plugins: [new AutoPlay(), new Perspective({ rotate: 0.2 })],
    };
  },
};
</script>

<style>
.flicking-camera {
  display: flex !important;
}
.panels {
  overflow: hidden;
}
</style>
