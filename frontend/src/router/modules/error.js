import The404Error from "@/components/ErrorPage/The404Error.vue";

const error = [
  {
    path: "/:pathMatch(.*)*",
    name: "the404Error",
    component: The404Error,
  },
];

export default error;
