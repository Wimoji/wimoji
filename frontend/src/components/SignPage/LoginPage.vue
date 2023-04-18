<template>
  <v-sheet class="sign-page col-transparent">
    <div class="blue-circle-area">
      <blue-circle></blue-circle>
    </div>
    <login-input></login-input>
  </v-sheet>
</template>

<script>
import LoginInput from "@/components/SignPage/LoginInput.vue";
import BlueCircle from "@/common/component/BlueCircle.vue";

export default {
  components: { LoginInput, BlueCircle },
};
</script>

<style></style>
