import DeleteUserButton from "@/components/atoms/Button/DeleteUserButton";
import { deleteUser } from "@/utils/axiosApi";
import { useRouter } from "next/router";

const DeleteUserComponent = () => {
  const router = useRouter();

  //회원탈퇴
  const handleDeleteUser = async () => {
    try {
      if (confirm("탈퇴하시나요? ＼（〇_ｏ）／")) {
        const result = await deleteUser();
        if (result.data.success) {
          alert("탈퇴가 완료되었습니다. /(ㄒoㄒ)/~~");
          //세션 스토리지에서 token 삭제
          sessionStorage.clear();
        }
      }
    } catch (error: any) {
      alert(error.response.data.message);
    }
    //페이지 이동
    router.push("/");
  };

  return <DeleteUserButton onClick={handleDeleteUser} />;
};

export default DeleteUserComponent;
