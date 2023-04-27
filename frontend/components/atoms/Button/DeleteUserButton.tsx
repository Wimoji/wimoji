interface DeleteUserButtonProps {
  onClick: () => void;
}

const DeleteUserButton: React.FC<DeleteUserButtonProps> = ({ onClick }) => {
  return (
    <button
      onClick={onClick}
      className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
    >
      회원 탈퇴
    </button>
  );
};

export default DeleteUserButton;
