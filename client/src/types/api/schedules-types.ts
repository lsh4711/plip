export interface Places {
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  stratDate: Date | string | number;
}

// POST : /schedules/write
export interface PostScheduleRequest {
  content: string;
  startDate: Date | string | number;
  endDate: Date | string | number;
  places: Places[];
}
