import ListHeader from "@/views/Header/ListHeader.vue";
import ListView from "@/views/ListView.vue";
import ChatPage from "@/components/ChatPage/ChatPage.vue";
import ChattingPage from "@/components/ChatPage/ChattingPage.vue";
import MyEmojiPage from "@/components/MyEmojiPage/MyEmojiPage.vue";

const list = [
  {
    path: "/list",
    name: "list",
    components: {
      header: ListHeader,
      default: ListView,
    },
    children: [
      {
        path: "/chat",
        name: "chat",
        components: {
          default: ChatPage,
        },
      },
      {
        path: "/chat/:roomId",
        name: "chatting",
        component: ChattingPage,
        // props: true,
      },
      {
        path: "/myEmoji",
        name: "myEmoji",
        component: MyEmojiPage,
      },
    ],
  },
];

export default list;
