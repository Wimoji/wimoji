<template>
  <div class="ellipse-container">
    <div class="ellipse1"></div>
    <div class="ellipse2"></div>
  </div>
</template>

<script>
export default {
  name: "YellowCircle",
};
</script>

<style>
.ellipse-container {
  position: relative;
  width: 400px;
  height: 400px;
}

.ellipse1,
.ellipse2 {
  position: absolute;
  width: 650px;
  height: 700px;
  border-radius: 50%;
}

.ellipse1 {
  background-color: var(--main-col-4);
  animation: rotate1 8s linear infinite;
}

.ellipse2 {
  background-color: rgba(255, 232, 153, 0.5);
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
