interface LogoutButtonProps {
  onClick: () => void;
}

const LogoutButton: React.FC<LogoutButtonProps> = ({ onClick }) => {
  return <button onClick={onClick}>로그아웃</button>;
};

export default LogoutButton;
