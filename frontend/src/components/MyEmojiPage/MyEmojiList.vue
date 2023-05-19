<template>
  <v-sheet color="var(--main-col-1)" v-if="myEmojis == null">
    <div class="xl-font text-center mt-10">나의 이모지가 없어요... 😂</div>
  </v-sheet>
  <v-sheet v-else color="var(--main-col-1)" class="in-my-emoji-list">
    <v-img
      class="emoji-item"
      v-for="(item, i) in myEmojis"
      :key="i"
      @click="detailEmoji(i)"
      :src="emojiCategory[item.eid].link"
    ></v-img>
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
import MyEmojiDetail from "@/components/MyEmojiPage/MyEmojiDetail.vue";

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
    const result = await getEmojis();
    this.myEmojis = result;
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

<style>
.in-my-emoji-list {
  height: 100%;
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
.in-my-emoji-list {
  display: grid;
  grid-template-columns: calc(100% / 3) calc(100% / 3) calc(100% / 3);
  margin: 0 auto;
}

.emoji-item {
  background-color: white;
  margin: 20px;
  width: 70%;
  border-radius: 50%;
}

@-webkit-keyframes emoji-item {
  to {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }
}
@keyframes emoji-item {
  to {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
  }
}
.emoji-item:active {
  -webkit-animation-name: emoji-item;
  animation-name: emoji-item;
  -webkit-animation-duration: 0.3s;
  animation-duration: 0.3s;
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
  -webkit-animation-direction: alternate;
  animation-direction: alternate;
}
</style>
