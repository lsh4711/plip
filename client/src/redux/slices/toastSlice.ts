import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { ToastProp } from '@/components/ui/toast/ToastItem';

export interface ToastInterface {
  id?: number;
  content: string;
  visible?: boolean;
  type?: ToastProp;
  [key: string]: unknown;
}

const initialState: ToastInterface[] = [];

const toastSlice = createSlice({
  name: 'toastSlice',
  initialState,
  reducers: {
    showToast: (state, action: PayloadAction<ToastInterface>) => {
      state.push(action.payload);
    },
    shiftToast: (state) => {
      state.shift();
    },
  },
});

export const { showToast, shiftToast } = toastSlice.actions;
export default toastSlice;
