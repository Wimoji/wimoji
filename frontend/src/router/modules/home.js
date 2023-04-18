import HomeHeader from "@/views/Header/HomeHeader.vue";
import HomePage from "@/components/HomePage/HomePage.vue";

const home = [
  {
    path: "/home",
    name: "home",
    components: {
      header: HomeHeader,
      default: HomePage,
    },
  },
];

export default home;
