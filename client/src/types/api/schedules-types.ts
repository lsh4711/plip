import { regions } from '@/datas/regions';
import { CategoryGroupCode } from '../mapApi/place-types';

export interface ScheduledPlace {
  placeId: number;
  scheduleId: number;
  schedulePlaceId: number;
  apiId: number;
  name: string;
  address: string;
  latitude: string;
  longitude: string;
  days: number;
  orders: number;
  bookmark: false;
  category: CategoryGroupCode;
}

// GET : /schedules/:id
export interface GetScheduleResponse {
  scheduleId: number;
  region: (typeof regions)[number];
  title: string;
  content: string;
  memberCount: number;
  startDate: Date;
  endDate: Date;
  createdAt: Date;
  modifiedAt: Date;
  memberId: number;
  nickname: string;
  places: ScheduledPlace[][];
}

// POST : /schedules/write
export interface PostScheduleRequest {
  title: string;
  content: string | null;
  region: (typeof regions)[number];
  startDate: Date;
  endDate: Date;
  places: ScheduledPlace[][] | null;
}
