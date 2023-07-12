import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface AccessTokenType {
  accesstoken: string;
}

interface InitialStataType extends AccessTokenType {
  isLogin: boolean;
}

const initialState: InitialStataType = {
  isLogin: false,
  accesstoken: '',
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
      state.accesstoken = '';
      state.isLogin = false;
    },
  },
});

export const { setAccessToken, setLogout } = authSlice.actions;
export default authSlice;
