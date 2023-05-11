<template>
  <div class="white-ellipse-container">
    <div class="white-ellipse1"></div>
    <div class="white-ellipse2"></div>
    <div class="white-text-area">
      <v-row v-for="(text, i) in propsText" :key="i">
        <v-col align="center">{{ text }}</v-col>
      </v-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "WhiteCircle",
  props: ["propsText"],
};
</script>

<style>
.white-ellipse-container {
  position: relative;
  width: 400px;
  height: 400px;
}
.white-text-area {
  position: absolute;
  transform: translate(-50%, -25%);
}
.white-ellipse1,
.white-ellipse2 {
  position: absolute;
  border-radius: 50%;
}

.white-ellipse1 {
  width: 410px;
  height: 400px;
  background: rgba(255, 255, 255, 0.31);
  border: 2px solid #ffffff;
  animation: rotate1 8s linear infinite;
}

.white-ellipse2 {
  width: 415px;
  height: 405px;
  background: rgba(255, 255, 255, 0.186);
  animation: rotate2 4s linear infinite;
}

@keyframes rotate1 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

@keyframes rotate2 {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(-360deg);
  }
}
</style>
