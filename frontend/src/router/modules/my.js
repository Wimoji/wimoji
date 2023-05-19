import MyPageView from "@/views/MyPageView.vue";
import ChatPage from "@/components/ChatPage/ChatPage.vue";
import ChattingPage from "@/components/ChatPage/ChattingPage.vue";
import MyEmojiPage from "@/components/MyEmojiPage/MyEmojiPage.vue";

const my = [
  {
    path: "/my",
    name: "my",
    components: {
      default: MyPageView,
    },
    children: [
      {
        path: "/my/chat",
        name: "chat",
        components: {
          default: ChatPage,
        },
      },
      {
        path: "/my/chat/:roomId",
        name: "chatting",
        components: {
          default: ChattingPage,
        },
      },
      {
        path: "/my/emoji",
        name: "emoji",
        components: {
          default: MyEmojiPage,
        },
      },
    ],
  },
];

export default my;
