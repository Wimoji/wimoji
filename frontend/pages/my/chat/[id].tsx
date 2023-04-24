import { useRouter } from "next/router";

export default function ChatIdPage() {
  const router = useRouter();
  const { id } = router.query;
  return (
    <div>
      <span>{id}의 채팅방 입니다.</span>
    </div>
  );
}
