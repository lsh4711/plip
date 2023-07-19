import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { ScheduledPlaceBase } from '@/types/api/schedules-types';

interface InitialState {
  searchPlaceResults: kakao.maps.services.PlacesSearchResult;
  selectedPlace: ScheduledPlaceBase | null;
}

const initialState: InitialState = {
  searchPlaceResults: [],
  selectedPlace: null,
};

const placeSlice = createSlice({
  name: 'searchPlaceSlice',
  initialState,
  reducers: {
    setSearchPlaceResults: (
      state,
      { payload }: PayloadAction<InitialState['searchPlaceResults']>
    ) => {
      state.searchPlaceResults = payload;
    },
    setSelectedPlace: (state, { payload }: PayloadAction<InitialState['selectedPlace']>) => {
      state.selectedPlace = payload;
    },
  },
});

export const { setSearchPlaceResults, setSelectedPlace } = placeSlice.actions;
export default placeSlice;
