<template>
  <v-sheet color="var(--col-empty)">
    <v-dialog v-model="dialog" width="85%">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          v-if="posFlag"
          rounded
          dark
          color="var(--main-col-3)"
          v-bind="attrs"
          v-on="on"
        >
          이모지 만들기<v-icon>mdi-heart-plus-outline</v-icon>
        </v-btn>
        <v-btn v-else disabled rounded v-bind="attrs" v-on="on">
          이모지 만들기<v-icon>mdi-heart-plus-outline</v-icon>
        </v-btn>
      </template>

      <v-card width="100%" class="pa-2 emoji-card">
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
                    <v-img :src="emojiCategory[nowEmoji].link"> </v-img>
                  </v-avatar>
                  {{ emojiCategory[nowEmoji].title }}
                </v-chip>
                <v-btn fab v-else
                  ><v-icon>{{ nowEmoji }}</v-icon></v-btn
                >
              </div>
            </template>
            <v-sheet class="emoji-category pa-3" elevation="10" rounded="xl">
              <emoji-list @changeEmoji="changeEmoji" />
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
          v-model="title"
        ></v-textarea>

        <v-combobox
          hide-details
          v-model="limit"
          outlined
          rounded
          :items="items"
          class="px-16"
          placeholder="인원수 선택"
        ></v-combobox>

        <v-card-actions class="d-flex justify-center">
          <v-btn rounded dark color="var(--main-col-3)" @click="goMakeEmoji">
            등록
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-sheet>
</template>

<script>
import EmojiList from "@/components/EmojiList/EmojiList.vue";
import { mapState, mapActions } from "vuex";
import { makeEmoji } from "@/api/modules/emoji";

import { makeChatRoom } from "@/api/modules/chat";
import { myChat } from "@/api/modules/user";

export default {
  components: {
    EmojiList,
  },
  props: ["posFlag"],
  data() {
    return {
      //dialog 관련 설정
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
      //이모지 관련 설정
      nowEmoji: "mdi-heart-plus-outline",
      title: "",
      latitude: null,
      longitude: null,
      myPosition: null,
      myDongcode: null,
      //인원
      items: [
        2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
        22, 23, 24, 25, 26, 27, 28, 29, 30,
      ],
      limit: null,
    };
  },
  computed: {
    ...mapState("emojiStore", ["emojiCategory"]),
    ...mapState("userStore", ["user", "location"]),
  },
  methods: {
    ...mapActions("userStore", ["setLocation"]),
    changeEmoji(emojiId) {
      // console.log("선택된 이모지 아이디 >> ", emojiId);
      this.nowEmoji = emojiId;
    },
    async goMakeEmoji() {
      //유효성 확인
      if (this.nowEmoji == "mdi-heart-plus-outline") {
        alert("이모지를 선택해주세요");
        return;
      }
      if (this.title.length <= 0) {
        alert("내용을 입력해주세요");
        return;
      }
      if (this.limit == null) {
        alert("최대 인원수를 선택해주세요");
        return;
      }

      console.log("이모지 생성 요청");
      let rid = null; //채팅방 아이디

      //채팅방 생성 API 호출
      await makeChatRoom(
        {
          eid: this.nowEmoji,
          title: this.title,
          limit: this.limit,
        },
        ({ data }) => {
          console.log(data);
          if (data.success) {
            rid = data.data.rid;
          }
        },
        (error) => {
          console.log(error);
        }
      );

      if (rid != null) {
        //emoji axios post 요청 처리
        const data = {
          eid: this.nowEmoji,
          title: this.title,
          latitude: this.latitude,
          longitude: this.longitude,
          dongCode: this.myDongcode,
          rid: rid,
        };
        await makeEmoji(
          data,
          ({ data }) => {
            // console.log(data);
            if (data.success) {
              alert("이모지 등록 완료!");
              //값 비워주기
              this.dialog = false;
              this.nowEmoji = "mdi-heart-plus-outline";
              this.title = "";
              this.limit = null;
            }
            this.$router.go();
          },
          (error) => {
            console.log(error);
          }
        );
      } else {
        alert("이모지 생성 중 오류가 발생했습니다. 다시 시도해주세요.");
        // this.$router.go(0);
      }

      // 유저 정보 수정 api 호출
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
    },
  },
};
</script>

<style>
/* .create-emoji-btn-area {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
.v-speed-dial {
  position: absolute;
}
.emoji-card {
  position: fixed;
  width: 80%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
 */
.emoji-category {
  position: fixed;
  top: 50%;
  transform: translateY(-50%);
  width: 100%;
}
</style>
