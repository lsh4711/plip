import { regions } from '@/datas/regions';
import { CategoryGroupCode } from '../mapApi/place-types';

export interface ScheduledPlace extends ScheduledPlaceBase {
  placeId: number;
  scheduleId: number;
  schedulePlaceId: number;
  days: number;
  orders: number;
}

export interface ScheduledPlaceBase {
  apiId: number;
  name: string;
  address: string;
  latitude: string;
  longitude: string;
  category: CategoryGroupCode;
  bookmark: false;
  // phone 추가 필요
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

// PATCH : /schedules/:id/edit
export interface PatchScheduleRequest {
  title: string;
  content: string | null;
  memberCount: number;
  region: (typeof regions)[number];
  startDate: Date;
  endDate: Date;
  places: ScheduledPlaceBase[][];
}
