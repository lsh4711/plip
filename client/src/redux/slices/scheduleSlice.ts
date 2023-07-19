import { PayloadAction, createSlice } from '@reduxjs/toolkit';

import { ScheduledPlaceBase } from '@/types/api/schedules-types';

interface InitialState {
  isStale: boolean;
  schedules: ScheduledPlaceBase[][];
}

const initialState: InitialState = {
  isStale: false,
  schedules: [[]],
};

const scheduleSlice = createSlice({
  name: 'scheduleSlice',
  initialState,
  reducers: {
    setIsStale: (state, { payload }: PayloadAction<InitialState['isStale']>) => {
      state.isStale = payload;
    },
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
    editSchedule: (
      state,
      { payload }: PayloadAction<{ dayNumber: number; schedule: ScheduledPlaceBase[] }>
    ) => {
      const { dayNumber, schedule } = payload;
      state.schedules.splice(dayNumber, 1, schedule);
    },
  },
});

export const { setIsStale, setSchedule, addSchedule, editSchedule } = scheduleSlice.actions;
export default scheduleSlice;
