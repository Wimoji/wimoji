<template>
  <v-sheet v-if="myEmojis.length != 0" color="var(--main-col-1)">
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
  <v-sheet color="var(--main-col-1)" v-else>
    <div class="xl-font text-center mt-10">나의 이모지가 없어요... 😂</div>
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
        console.log(data);
        if (data.success) {
          console.log("내 이모지들 화면에 저장하자");
          // this.myEmojis = data.data;
          this.myEmojis = [];
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
  mounted() {},
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
.v-sheet {
  padding-top: 130px;
}
.detail-emoji-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  max-width: 500px;
  transform: translate(-50%, -50%);
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
