import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface ChatRoom {
  id: string;
  rid: string;
  eid: number;
  title: string;
  participant: number;
  limit: number;
  isNew: boolean;
}

export interface UserState {
  isLogin: boolean;
  nowChatRoom: ChatRoom;
}

const initialState: UserState = {
  isLogin: false,
  nowChatRoom: {
    id: "",
    rid: "",
    eid: 0,
    title: "",
    participant: 0,
    limit: 0,
    isNew: false,
  },
};

const UserSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    changeUserState: (state: UserState, action: PayloadAction<boolean>) => {
      state.isLogin = action.payload;
    },
    changeNowChatRoomState: (state, action) => {
      state.nowChatRoom = action.payload;
    },
  },
});

export const { changeUserState, changeNowChatRoomState } = UserSlice.actions;
export default UserSlice;
