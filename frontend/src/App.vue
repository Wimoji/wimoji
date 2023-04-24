<template>
  <v-app>
    <!-- Header -->
    <router-view name="header" />
    <!-- Main -->
    <v-main>
      <router-view />
    </v-main>
    <!-- Footer -->
    <router-view name="footer" />
  </v-app>
</template>

<script>
export default {
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
</style>
