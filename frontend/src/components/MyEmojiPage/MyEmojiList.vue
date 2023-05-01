<template>
  <v-sheet
    class="d-flex justify-space-around flex-wrap"
    color="var(--main-col-1)"
  >
    <v-sheet
      class="my-5"
      rounded="circle"
      v-for="(item, i) in myEmojis"
      :key="i"
      @click="detailEmoji(i)"
    >
      <v-img
        class="ma-4"
        :src="emojiCategory[item.eid].link"
        width="4rem"
      ></v-img>
    </v-sheet>
    <transition name="moveInUp">
      <my-emoji-detail
        @closeEmoji="closeEmoji"
        class="detail-emoji-modal"
        v-if="selectedEmoji != null"
        :selectedEmoji="selectedEmoji"
      />
    </transition>
  </v-sheet>
</template>

<script>
import { getEmojis } from "@/api/modules/emoji";
import { mapState } from "vuex";
import MyEmojiDetail from "./MyEmojiDetail.vue";

export default {
  components: { MyEmojiDetail },
  data() {
    return {
      myEmojis: null,
      selectedEmoji: null,
    };
  },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  async mounted() {
    //나의 이모지 요청
    await getEmojis(
      ({ data }) => {
        // console.log(data);
        if (data.success) {
          this.myEmojis = data.data;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    detailEmoji(index) {
      //선택한 이모지를 props로 보내기
      const eid = this.myEmojis[index].eid;
      const content = this.myEmojis[index].content;

      this.selectedEmoji = {
        eid: eid,
        index: index,
        link: this.emojiCategory[eid].link,
        content: content,
      };
    },
    closeEmoji() {
      this.selectedEmoji = null;
    },
  },
};
</script>

<style scoped>
.detail-emoji-modal {
  position: fixed;
  top: 35%;
  width: 80%;
}
.moveInUp-enter-active {
  opacity: 0;
  transition: opacity 0.1s ease-in;
  animation: fadeIn 0.1s ease-in;
}
.moveInUp-leave-active {
  opacity: 0;
  transition: opacity 0.1s ease-in;
  animation: fadeOut 0.1s ease-in;
}
@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}
</style>
