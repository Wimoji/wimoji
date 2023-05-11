<template>
  <v-sheet class="sign-area col-transparent">
    <div class="login-blue-circle"><blue-circle></blue-circle></div>
    <div class="login-input"><login-input></login-input></div>
    <the-footer></the-footer>
  </v-sheet>
</template>

<script>
import LoginInput from "@/components/SignPage/LoginInput.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";
import TheFooter from "@/views/TheFooter.vue";

export default {
  components: { LoginInput, BlueCircle, TheFooter },
};
</script>

<style>
.sign-area {
  height: 100%;
}
.sign-area .login-blue-circle {
  position: absolute;
  top: 15%;
  left: 40%;
}
.sign-area .login-blue-circle .blue-ellipse-container .blue-ellipse2 {
  position: absolute;
  top: 10%;
}
.login-input {
  position: relative;
}
footer {
  position: fixed;
  bottom: 0;
  margin-top: 20%;
}
</style>
