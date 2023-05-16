import { getAroundEmojis } from "@/lib/emoji";

const AroundEmojis = () => {
  const data = {
    latitude: "37.5128064",
    longitude: "127.0284288",
    dongCode: "1168010800",
  };

  try {
    const result = getAroundEmojis(data);
    console.log("이모지 불러옴 >>", result);
  } catch (error) {
    console.log(error);
  }

  return (
    <div>
      <h1>주변 이모지 화면</h1>
    </div>
  );
};

export default AroundEmojis;
