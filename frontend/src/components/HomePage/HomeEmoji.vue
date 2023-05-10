<template>
  <v-container>
    <v-avatar
      size="80"
      color="white"
      v-for="(item, i) in aroundEmojis"
      :key="i"
      @click="detailAroundEmoji(i)"
    >
      <v-img :src="emojiCategory[item.eid].link"></v-img>
    </v-avatar>
    <v-card v-if="isClickEmoji">
      <v-avatar>
        <v-img :src="emojiCategory[selectedEmoji.eid].link"></v-img>
      </v-avatar>
      <v-card-title>{{ selectedEmoji.title }}</v-card-title>
      <v-btn
        height="3em"
        width="6em"
        rounded
        color="var(--main-col-3)"
        class="white-col-1"
        @click="joinChat"
        >함께하기
      </v-btn>
    </v-card>
  </v-container>
</template>

<script>
import { myChat } from "@/api/modules/user";
import { getAroundEmojis } from "@/api/modules/emoji";
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      // aroundEmojis: [], //사용자 주변의 이모지들
      isClickEmoji: false,
      selectedEmoji: null,
    };
  },
  computed: {
    ...mapState("userStore", ["location", "aroundEmojis"]),
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  async created() {
    //지금 dongcode로 주변 사용자의 이모지 불러오기
    if (this.location.dongCode != null) {
      let result = await getAroundEmojis(this.location);
      if (result == null) {
        console.log("주변 이모지 불러오기 오류 발생");
      } else {
        // console.log("result >> ", result);
        // this.aroundEmojis = result;
        this.setAroundEmojis(result);
      }
      //result가 null이라면 오류, result.length가 0이라면 주변 이모지 없음
    }
  },
  methods: {
    ...mapActions("chatStore", ["setNowChatRoom"]),
    ...mapActions("userStore", ["setAroundEmojis"]),
    detailAroundEmoji(index) {
      //사용자의 상세 이모지 보기
      this.isClickEmoji = true;
      this.selectedEmoji = this.aroundEmojis[index];
    },
    async joinChat() {
      //지금 선택된 이모지의 채팅방 참여하기
      this.setNowChatRoom(this.selectedEmoji);
      const params = {
        rid: rid,
      };
      await myChat(
        params,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
      this.$router.push({
        name: "chatting",
        params: { roomId: this.selectedEmoji.rid, data: this.selectedEmoji },
      });
    },
  },
};
</script>

<style></style>
