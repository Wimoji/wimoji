import {
  configureStore,
  combineReducers,
  AnyAction,
  CombinedState,
} from "@reduxjs/toolkit";
import UserSlice, { UserState } from "./UserSlice";
import { createWrapper, HYDRATE } from "next-redux-wrapper";
import { Reducer } from "@reduxjs/toolkit";

export interface RootState {
  user: UserState;
}

const rootReducer = (
  state: RootState,
  action: AnyAction
): CombinedState<RootState> => {
  if (action.type == HYDRATE) return { ...state, ...action.payload };
  const combinedReducer = combineReducers({
    user: UserSlice.reducer,
  });
  return combinedReducer(state, action);
};

const makeStore = () =>
  configureStore({
    reducer: rootReducer as Reducer<CombinedState<RootState>, AnyAction>,
    devTools: process.env.NODE_ENV === "development",
  });

export const wrapper = createWrapper(makeStore);
