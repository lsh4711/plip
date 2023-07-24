import { regions } from '@/datas/regions';
import { CategoryGroupCode } from '../mapApi/place-types';
import { Record } from './records-types';

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
  bookmark: boolean;
  phone: string;
  placeId?: number;
  scheduleId?: number;
  schedulePlaceId?: number;

  // TODO phone 추가 필요
}

// GET : /schedules/:id
export interface Schedule {
  scheduleId: number;
  region: (typeof regions)[number];
  korRegion: string;
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

export interface GetScheduleResponse {
  recordsMap: Record[][];
  schedule: Schedule;
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
