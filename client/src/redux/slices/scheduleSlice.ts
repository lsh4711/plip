import { PayloadAction, createSlice } from '@reduxjs/toolkit';

import { ScheduledPlaceBase } from '@/types/api/schedules-types';

interface InitialState {
  schedules: ScheduledPlaceBase[][];
}

const initialState: InitialState = {
  schedules: [[]],
};

const scheduleSlice = createSlice({
  name: 'scheduleSlice',
  initialState,
  reducers: {
    setSchedule: (state, { payload }: PayloadAction<InitialState['schedules']>) => {
      state.schedules = payload;
    },
    addSchedule: (
      state,
      { payload }: PayloadAction<{ day: number; schedule: ScheduledPlaceBase }>
    ) => {
      const { day, schedule } = payload;
      state.schedules[day - 1].push(schedule);
    },
  },
});

export const { setSchedule, addSchedule } = scheduleSlice.actions;
export default scheduleSlice;
