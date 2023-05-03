<template>
  <v-app>
    <!-- Header -->
    <header-view></header-view>
    <!-- Main -->
    <v-main>
      <transition name="moveInUp">
        <router-view />
      </transition>
    </v-main>
    <!-- Footer -->
    <router-view name="footer" />
  </v-app>
</template>

<script>
import HeaderView from "@/views/HeaderView.vue";

export default {
  components: { HeaderView },
  name: "App",

  data: () => ({
    //
  }),
};
</script>

<style>
@import "@/assets/styles/font.css";
@import "@/assets/styles/text.css";
@import "@/assets/styles/variable.css";
@import "@/assets/styles/override.css";
@import "@/assets/styles/input.css";

html body {
  background: #fafafa;
  max-width: 500px;
  margin: 0 auto;
  height: 100%;
  min-height: 100%;
}
#app {
  background-color: var(--main-col-1);
  max-width: 100%;
  min-height: 100%;

  font-family: var(--main-font-1);
}
.moveInUp-enter-active {
  opacity: 0;
  transition: opacity 0.3s ease-in;
}
.moveInUp-enter-active {
  animation: fadeIn 0.3s ease-in;
}
@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}
</style>
