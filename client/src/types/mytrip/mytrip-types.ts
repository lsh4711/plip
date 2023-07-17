import { regions } from '@/datas/regions';

// 나의 일정
export interface MyTripTypes {
  scheduleId: string;
  title: string;
  region: (typeof regions)[number];
  memberCount: number;
  placeCount: number;
  isEnd: boolean;
  createdAt: Date;
  modifiedAt: Date;
  startDate: Date;
  endDate: Date;
}
