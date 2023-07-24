import { configureStore } from '@reduxjs/toolkit';
import { authSlice } from './slices';
import scheduleSlice from './slices/scheduleSlice';
import placeSlice from './slices/placeSlice';
import toastSlice from './slices/toastSlice';
import recordsSlice from './slices/recordsSlice';

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    toast: toastSlice.reducer,
    place: placeSlice.reducer,
    schedule: scheduleSlice.reducer,
    records: recordsSlice.reducer,
  },
});

export default store;
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
