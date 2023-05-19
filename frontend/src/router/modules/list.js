import ListHeader from "@/views/Header/ListHeader.vue";
import ListView from "@/views/ListView.vue";
import ChatPage from "@/components/ChatPage/ChatPage.vue";
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
        component: ChatPage,
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
