<template>
  <v-sheet class="sign-area col-transparent">
    <the-footer></the-footer>
    <div class="signup-yellow-circle"><yellow-circle></yellow-circle></div>
    <div class="signup-input"><signup-input></signup-input></div>
  </v-sheet>
</template>

<script>
import SignupInput from "@/components/SignPage/SignupInput.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";
import TheFooter from "@/views/TheFooter.vue";

export default {
  components: { SignupInput, YellowCircle, TheFooter },
};
</script>

<style>
.sign-area .signup-yellow-circle {
  position: absolute;
  top: 15%;
  left: 40%;
}
.sign-area .signup-yellow-circle .ellipse-container .ellipse2 {
  position: absolute;
  top: 10%;
}
.signup-input {
  position: relative;
}
</style>
