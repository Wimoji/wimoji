<template>
  <v-sheet class="sign-page col-transparent">
    <div class="yellow-circle-area">
      <yellow-circle></yellow-circle>
    </div>
    <signup-input></signup-input>
  </v-sheet>
</template>

<script>
import SignupInput from "@/components/SignPage/SignupInput.vue";
import YellowCircle from "@/common/component/YellowCircle.vue";

export default {
  components: { SignupInput, YellowCircle },
};
</script>

<style></style>
