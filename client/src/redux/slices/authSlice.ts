import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export const EMPTY_TOKEN = 'empty';

interface AccessTokenType {
  accesstoken: string | typeof EMPTY_TOKEN;
}

interface InitialStataType extends AccessTokenType {
  isLogin: boolean;
}

const initialState: InitialStataType = {
  isLogin: false,
  accesstoken: EMPTY_TOKEN,
};

const authSlice = createSlice({
  name: 'authSlice',
  initialState,
  reducers: {
    setAccessToken: (state, action: PayloadAction<AccessTokenType>) => {
      state.accesstoken = action.payload.accesstoken;
      state.isLogin = true;
    },
    setLogout: (state) => {
      state.accesstoken = EMPTY_TOKEN;
      state.isLogin = false;
      console.log(state);
    },
  },
});

export const { setAccessToken, setLogout } = authSlice.actions;
export default authSlice;
