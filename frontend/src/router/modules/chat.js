import ChatPage from "@/components/ChatPage/ChatPage.vue";
import ChattingPage from "@/components/ChatPage/ChattingPage.vue";

const chat = [
  {
    path: "/chat",
    name: "chat",
    components: {
      default: ChatPage,
    },
  },
  {
    path: "/chatting",
    name: "chatting",
    component: ChattingPage,
  },
];

export default chat;
