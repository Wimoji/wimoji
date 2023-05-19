<template>
  <v-toolbar flat height="85px">
    <v-row>
      <v-col align-self="center">
        <v-btn icon @click="goBack">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </v-col>
      <v-col>
        <v-toolbar-title class="d-flex flex-column align-center">
          <v-avatar size="35">
            <v-img :src="emojiCategory[nowChatRoom.eid].link"></v-img>
          </v-avatar>
          <div class="xs-font main-font-bd pt-2">{{ nowChatRoom.title }}</div>
        </v-toolbar-title>
      </v-col>
      <v-col align="right" align-self="center">
        <v-btn icon @click="deleteChat">
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </v-toolbar>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  computed: {
    ...mapState("chatStore", ["nowChatRoom"]),
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  methods: {
    ...mapActions("chatStore", ["setIsDelete"]),
    goBack() {
      if (this.$router.history.length > 1) {
        this.$router.go(-1);
      } else {
        this.$router.push("/my/chat");
      }
    },
    deleteChat() {
      if (confirm("채팅방을 삭제하시나요?")) {
        this.setIsDelete(true);
      }
    },
  },
};
</script>

<style scoped></style>
