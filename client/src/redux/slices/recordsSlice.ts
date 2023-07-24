import { Record } from '@/types/api/records-types';
import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface InitialState {
  records: Record[][];
}

const initialState: InitialState = {
  records: [],
};

const recordsSlice = createSlice({
  name: 'recordsSlice',
  initialState,
  reducers: {
    setRecords: (state, { payload }: PayloadAction<InitialState['records']>) => {
      state.records = payload;
    },
  },
});

export const { setRecords } = recordsSlice.actions;
export default recordsSlice;
