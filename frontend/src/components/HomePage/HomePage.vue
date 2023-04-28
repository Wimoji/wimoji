<template>
  <v-container>
    <div>사용자 주변의 이모지가 보이는 홈페이지입니다.</div>

    <v-dialog v-model="dialog" width="45%">
      <template v-slot:activator="{ on, attrs }">
        <v-btn rounded dark color="var(--main-col-3)" v-bind="attrs" v-on="on">
          이모지 만들기<v-icon>mdi-heart-plus-outline</v-icon>
        </v-btn>
      </template>

      <v-card class="pa-2">
        <v-card-title class="text-h5 d-flex align-center justify-center">
          <v-speed-dial
            v-model="fab"
            top
            :direction="direction"
            :open-on-hover="hover"
            :transition="transition"
          >
            <template v-slot:activator>
              <v-btn fab v-if="fab"><v-icon>mdi-close</v-icon></v-btn>
              <div v-else>
                <v-chip v-if="nowEmoji != 'mdi-heart-plus-outline'">
                  <v-avatar left>
                    <v-img :src="emojiCategory[nowEmoji - 1].link"> </v-img>
                  </v-avatar>
                  {{ emojiCategory[nowEmoji - 1].title }}
                </v-chip>
                <v-btn fab v-else
                  ><v-icon>{{ nowEmoji }}</v-icon></v-btn
                >
              </div>
            </template>
            <v-sheet class="emoji-category pa-3" elevation="10" rounded="xl">
              <emoji-list :nowEmoji="nowEmoji" @changeEmoji="changeEmoji" />
            </v-sheet>
          </v-speed-dial>
        </v-card-title>
        <v-textarea
          label="내용을 입력하세요"
          rows="3"
          row-height="25"
          outlined
          class="pa-5 mt-8"
          hide-details
        ></v-textarea>

        <v-card-actions class="d-flex justify-center">
          <v-btn rounded dark color="var(--main-col-3)" @click="goMakeEmoji">
            등록
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import EmojiList from "../EmojiList/EmojiList.vue";
import { mapState } from "vuex";

export default {
  components: {
    EmojiList,
  },
  data() {
    return {
      dialog: false,
      direction: "bottom",
      fab: false,
      fling: false,
      hover: false,
      tabs: null,
      top: true,
      right: true,
      bottom: false,
      left: false,
      transition: "slide-y-transition",
      nowEmoji: "mdi-heart-plus-outline",
    };
  },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
  },
  methods: {
    changeEmoji(emojiId) {
      // console.log("선택된 이모지 아이디 >> ", emojiId);
      this.nowEmoji = emojiId;
    },
    goMakeEmoji() {
      //emoji axios post 요청 처리
      this.dialog = false;
      this.nowEmoji = "mdi-heart-plus-outline";
    },
  },
};
</script>

<style scoped>
.v-speed-dial {
  position: absolute;
}
.v-btn--floating {
  position: relative;
}
.emoji-category {
  position: fixed;
  width: 40%;
}
</style>
