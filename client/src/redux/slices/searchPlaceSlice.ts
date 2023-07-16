import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface InitialState {
  results: kakao.maps.services.PlacesSearchResult;
}

const initialState: InitialState = {
  results: [],
};

const searchPlaceSlice = createSlice({
  name: 'searchPlaceSlice',
  initialState,
  reducers: {
    setResult: (state, { payload }: PayloadAction<InitialState['results']>) => {
      state.results = payload;
    },
  },
});

export const { setResult } = searchPlaceSlice.actions;
export default searchPlaceSlice;
