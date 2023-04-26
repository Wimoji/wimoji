import React from "react";

interface DeleteUserButtonProps {
  onClick: () => void;
}

const DeleteUserButton: React.FC<DeleteUserButtonProps> = ({ onClick }) => {
  return <button onClick={onClick}>회원 탈퇴</button>;
};

export default DeleteUserButton;
