<template>
  <v-sheet>
    <v-toolbar flat height="85px">
      <v-row align="center" justify="space-between">
        <v-col align="left">
          <v-btn icon @click="goBack">
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
        </v-col>
        <v-col align="center">
          <v-toolbar-title>
            <div class="lg-font">마이 페이지</div>
          </v-toolbar-title>
        </v-col>
        <v-col align="right">
          <v-btn icon @click="deleteUser">
            <v-icon>mdi-account-remove-outline</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-toolbar>

    <v-tabs fixed-tabs>
      <v-tab to="/my/chat">채팅</v-tab>
      <v-tab to="/my/emoji">나의 이모지</v-tab>
    </v-tabs>
  </v-sheet>
</template>

<script>
import { deleteUser } from "@/api/modules/user";
import { mapState, mapActions } from "vuex";

export default {
  computed: {
    ...mapState("userStore", ["user"]),
  },
  methods: {
    ...mapActions("userStore", ["setLogout"]),
    goBack() {
      if (this.$router.history.length > 1) {
        this.$router.go(-1);
      } else {
        this.$router.push("/");
      }
    },
    async deleteUser() {
      if (confirm("회원 탈퇴 하시겠습니까?")) {
        await deleteUser(
          ({ data }) => {
            console.log(data);
            if (data.success) {
              alert("회원 탈퇴가 완료되었습니다.");
              //세션에서 유저 정보 제거
              sessionStorage.clear();
              //로그인 상태 변경
              this.setLogout();
              this.$router.push("/");
            }
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
  },
};
</script>

<style></style>
