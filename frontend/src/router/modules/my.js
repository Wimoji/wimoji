import MyHeader from "@/views/Header/MyHeader.vue";
import MyPageView from "@/views/MyPageView.vue";
import ChatPage from "@/components/ChatPage/ChatPage.vue";
import ChattingPage from "@/components/ChatPage/ChattingPage.vue";
import MyEmojiPage from "@/components/MyEmojiPage/MyEmojiPage.vue";

const my = [
  {
    path: "/my",
    name: "my",
    components: {
      header: MyHeader,
      default: MyPageView,
    },
    children: [
      {
        path: "/chat",
        name: "chat",
        components: {
          header: MyHeader,
          default: ChatPage,
        },
      },
      {
        path: "/chat/:roomId",
        name: "chatting",
        component: ChattingPage,
      },
      {
        path: "/emoji",
        name: "emoji",
        components: {
          header: MyHeader,
          default: MyEmojiPage,
        },
      },
    ],
  },
];

export default my;
