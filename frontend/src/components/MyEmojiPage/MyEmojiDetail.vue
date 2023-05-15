<template>
  <v-card class="d-flex flex-column align-center pa-5">
    <v-skeleton-loader
      v-if="selectedEmoji.link == null"
      class="mx-auto"
      type="avatar"
    ></v-skeleton-loader>
    <v-img v-else width="30%" :src="selectedEmoji.link"></v-img>
    <div class="md-font my-4">{{ selectedEmoji.title }}</div>
    <v-row class="d-flex">
      <v-col>
        <v-btn
          height="2.7em"
          width="6.5em"
          rounded
          color="var(--main-col-6)"
          class="white-col-1 xs-font"
          @click="deleteEmoji"
          >삭제</v-btn
        >
      </v-col>
      <v-col>
        <v-btn
          height="2.7em"
          width="6.5em"
          rounded
          color="var(--text-col-4)"
          class="white-col-1 xs-font"
          @click="closeEmoji"
          >닫기</v-btn
        >
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import { deleteEmoji } from "@/api/modules/emoji";
import { mapState } from "vuex";

export default {
  props: ["selectedEmoji"],
  data() {
    return {};
  },
  computed: {
    ...mapState("userStore", ["user"]),
  },
  methods: {
    closeEmoji() {
      this.$emit("closeEmoji");
    },
    async deleteEmoji() {
      const data = {
        eid: this.selectedEmoji.eid,
        order: this.selectedEmoji.index,
      };

      await deleteEmoji(
        data,
        ({ data }) => {
          // console.log("data", data);
          if (data.success) {
            alert("삭제되었습니다.");
            this.$router.go();
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style scoped></style>
