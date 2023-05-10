<template>
  <v-container fluid>
    <v-sheet
      class="spinner"
      color="var(--col-empty)"
      v-for="(item, i) in emojiCategory"
      :key="i"
    >
      <v-img
        v-if="i % 2 == 0"
        class="dot1"
        :src="item.link"
        :style="{
          top: radius * Math.sin(((i + 1) * Math.PI) / angle) + 'px',
          left: radius * Math.cos(((i + 1) * Math.PI) / angle) + 'px',
        }"
      >
      </v-img>

      <v-img
        v-else
        class="dot2"
        :src="item.link"
        :style="{
          top: radius * Math.sin(((i + 1) * Math.PI) / angle) + 'px',
          left: radius * Math.cos(((i + 1) * Math.PI) / angle) + 'px',
        }"
      >
      </v-img>
    </v-sheet>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  data() {
    return {
      radius: 155,
      angle: 6,
    };
  },
  created() {
    console.log(this.x, this.y);
  },
};
</script>

<style scoped>
.container {
  position: fixed;
  top: 0;
  left: 0;
}
.spinner {
  /*margin-left: -20px;*/
  /* margin-top: -20px; */
  z-index: 5;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  /* -webkit-animation: sk-rotate 2.0s infinite linear; */
  /* animation: sk-rotate 2.0s infinite linear; */
}

.dot1,
.dot2 {
  /* position: absolute; */
  /* display: inline-block; */
  width: 4rem;
  -webkit-animation: sk-bounce 2s infinite ease-in-out;
  animation: sk-bounce 2s infinite ease-in-out;
}

.dot2 {
  -webkit-animation-delay: -1s;
  animation-delay: -1s;
}

/* @-webkit-keyframes sk-rotate { 100% { -webkit-transform: rotate(360deg) }}
@keyframes sk-rotate { 100% { transform: rotate(360deg); -webkit-transform: rotate(360deg) }} */

@-webkit-keyframes sk-bounce {
  0%,
  100% {
    -webkit-transform: scale(0.7);
  }
  50% {
    -webkit-transform: scale(1);
  }
}

@keyframes sk-bounce {
  0%,
  100% {
    transform: scale(0.7);
    -webkit-transform: scale(0.7);
  }
  50% {
    transform: scale(1);
    -webkit-transform: scale(1);
  }
}
</style>
