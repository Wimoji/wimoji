<template>
  <div class="blue-ellipse-container">
    <div class="blue-ellipse1"></div>
    <div class="blue-ellipse2"></div>
  </div>
</template>

<script>
export default {
  name: "BlueCircle",
};
</script>

<style>
.blue-ellipse-container {
  position: relative;
  width: 400px;
  height: 400px;
}

.blue-ellipse1,
.blue-ellipse2 {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 750px;
  height: 800px;
  border-radius: 50%;
}

.blue-ellipse1 {
  background-color: var(--main-col-2);
  animation: rotate1 8s linear infinite;
}

.blue-ellipse2 {
  top: 55%;
  background-color: rgba(166, 214, 255, 0.5);
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
