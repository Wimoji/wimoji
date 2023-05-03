<template>
  <v-sheet color="var(--main-col-1)">
    <v-avatar
      size="80"
      color="white"
      v-for="(item, i) in myEmojis"
      :key="i"
      @click="detailEmoji(i)"
    >
      <v-img :src="emojiCategory[item.eid].link"></v-img>
    </v-avatar>
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
      myEmojis: [],
      selectedEmoji: null,
      // lines: "",
    };
  },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  async created() {
    //나의 이모지 요청
    await getEmojis(
      ({ data }) => {
        // console.log(data);
        if (data.success) {
          // this.myEmojis = data.data;
          data.data.forEach((element) => {
            this.myEmojis.push({ eid: element.eid, title: element.title });
          });
          console.log(this.myEmojis);
          // this.lines = this.myEmojis.length / 3;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  },
  async mounted() {
    // //나의 이모지 요청
    // await getEmojis(
    //   ({ data }) => {
    //     // console.log(data);
    //     if (data.success) {
    //       this.myEmojis = data.data;
    //       console.log(this.myEmojis);
    //       this.lines = this.myEmojis.length / 3;
    //     }
    //   },
    //   (error) => {
    //     console.log(error);
    //   }
    // );
  },
  methods: {
    detailEmoji(index) {
      //선택한 이모지를 props로 보내기
      const eid = this.myEmojis[index].eid;
      const title = this.myEmojis[index].title;

      this.selectedEmoji = {
        eid: eid,
        index: index,
        link: this.emojiCategory[eid].link,
        title: title,
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
