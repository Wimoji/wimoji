import { createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface UserState {
  isLogin: boolean;
}

const initialState: UserState = {
  isLogin: false,
};

const UserSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    changeUserState: (state: UserState, action: PayloadAction<boolean>) => {
      state.isLogin = action.payload;
    },
  },
});

export const { changeUserState } = UserSlice.actions;
export default UserSlice;
