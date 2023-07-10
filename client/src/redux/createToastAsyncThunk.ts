import { toastObjectCreator } from './../utils/toastObjectCreator';
import { createAsyncThunk } from '@reduxjs/toolkit';
import toastSlice, { ToastInterface } from './slices/toastSlice';

export const createToastAsyncThunk = createAsyncThunk<Promise<void>, ToastInterface | string>(
  'toast/createToastAsyncThunk',
  async (message, { dispatch }) => {
    const toastObject = toastObjectCreator(message);
    dispatch(toastSlice.actions.showToast(toastObject));
    setTimeout(() => {
      dispatch(toastSlice.actions.shiftToast());
    }, 4000);
  }
);
