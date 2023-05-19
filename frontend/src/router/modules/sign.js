import LoginPage from "@/components/SignPage/LoginPage.vue";
import SignupPage from "@/components/SignPage/SignupPage.vue";

const sign = [
  {
    path: "/login",
    name: "login",
    components: {
      default: LoginPage,
    },
  },
  {
    path: "/signup",
    name: "signup",
    components: {
      default: SignupPage,
    },
  },
];

export default sign;
