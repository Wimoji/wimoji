type Props = {
  params: {
    chatId: string;
  };
};

export default function ChatId({ params: { chatId } }: Props) {
  return (
    <div>
      <span>채팅방 아이디... {chatId}</span>
    </div>
  );
}
