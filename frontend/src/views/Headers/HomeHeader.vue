<template>
  <v-speed-dial
    v-model="fab"
    :top="top"
    :bottom="bottom"
    :right="right"
    :left="left"
    :direction="direction"
    :open-on-hover="hover"
    :transition="transition"
  >
    <template v-slot:activator>
      <v-btn v-model="fab" color="white" fab>
        <v-icon v-if="fab"> mdi-close </v-icon>
        <v-icon v-else> mdi-menu </v-icon>
      </v-btn>
    </template>
    <v-btn fab color="white" @click="goChat">
      <v-icon>mdi-chat-outline</v-icon>
    </v-btn>
    <v-btn fab color="white" @click="goLogout">
      <v-icon>mdi-logout</v-icon>
    </v-btn>
  </v-speed-dial>
</template>

<script>
import { logout } from "@/api/modules/user";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      isClicked: false,
      direction: "bottom",
      fab: false,
      fling: false,
      hover: false,
      tabs: null,
      top: true,
      right: true,
      bottom: false,
      left: false,
      transition: "slide-y-transition",
    };
  },
  methods: {
    ...mapActions("userStore", ["setLogout"]),
    goChat() {
      this.$router.push("/my/chat");
    },
    async goLogout() {
      console.log("로그아웃 합니다");
      await logout(
        ({ data }) => {
          // console.log(data);
          if (data.success) {
            alert("로그아웃 되었습니다.");
            //로그인 상태 변경
            this.setLogout();
            //세션에서 유저 정보 제거
            // sessionStorage.clear();
            // this.$router.go(0);
          }
        },
        (error) => {
          if (error.response.data != null) {
            alert(error.response.data.message);
          } else {
            console.log("logout error >>> "), error;
          }
        }
      );
      //세션에서 유저 정보 제거
      sessionStorage.clear();
      this.$router.go(0);
    },
  },
};
</script>

<style scoped>
.v-speed-dial {
  position: absolute;
}
.v-btn--floating {
  position: relative;
}
</style>
