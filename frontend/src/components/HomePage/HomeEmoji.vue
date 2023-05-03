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
import { getAroundEmojis } from "@/api/modules/emoji";
import { mapState } from "vuex";
export default {
  data() {
    return {
      aroundEmojis: [], //사용자 주변의 이모지들
      isClickEmoji: false,
      selectedEmoji: null,
    };
  },
  computed: {
    ...mapState("userStore", ["location"]),
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  async mounted() {
    //지금 dongcode로 주변 사용자의 이모지 불러오기
    // console.log("주변이모지불러오기");
    // console.log("this location", this.location);
    if (this.location.dongCode != null) {
      // console.log("요청을 보냄");
      await getAroundEmojis(
        this.location,
        ({ data }) => {
          // console.log("this location2", this.location);
          // console.log("around emojis", data);
          if (data.success) {
            this.aroundEmojis = data.data;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    }
  },
  methods: {
    detailAroundEmoji(index) {
      //사용자의 상세 이모지 보기
      this.isClickEmoji = true;
      this.selectedEmoji = this.aroundEmojis[index];
    },
    joinChat() {
      //지금 선택된 이모지의 채팅방 참여하기
      // this.selectedEmoji;
    },
  },
};
</script>

<style></style>
