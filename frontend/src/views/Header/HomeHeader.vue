<template>
  <!-- 홈 페이지의 헤더입니다. 이모지 생성, 채팅, 로그아웃 설정 -->
  <v-menu offset-y v-model="menu" @click="menu = !menu">
    <template v-slot:activator="{ on }">
      <v-btn color="var(--text-col-1)" dark v-on="on" icon>
        <v-icon>{{ menu ? "mdi-close" : "mdi-menu" }}</v-icon>
      </v-btn>
    </template>
    <v-list>
      <v-list-item>
        <v-btn icon @click="goList">
          <v-icon>mdi-chat-outline</v-icon>
        </v-btn>
      </v-list-item>
      <v-list-item>
        <v-btn icon @click="goLogout">
          <v-icon>mdi-logout</v-icon>
        </v-btn>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script>
import { logout } from "@/api/modules/user";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      isClicked: false,
      menu: false,
    };
  },
  methods: {
    ...mapActions("userStore", ["setIsLogin"]),
    goList() {
      this.$router.push("/chat");
    },
    async goLogout() {
      console.log("로그아웃 합니다");
      await logout(
        ({ data }) => {
          // console.log(data);
          if (data.success) {
            alert("로그아웃 되었습니다.");
            //세션에서 토큰 제거
            sessionStorage.clear();
            //로그인 상태 변경
            this.setIsLogin(false);
            this.$router.go(0);
          }
        },
        (error) => {
          alert(error.response.data.message);
        }
      );
    },
  },
};
</script>

<style></style>
