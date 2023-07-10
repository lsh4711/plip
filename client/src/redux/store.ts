import { configureStore } from '@reduxjs/toolkit';
import { authSlice } from './slices';
import toastSlice from './slices/toastSlice';

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    toast: toastSlice.reducer,
  },
});

export default store;
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
