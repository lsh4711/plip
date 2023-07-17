// 나의 일정
export interface MyTripTypes {
  scheduleId: string;
  title: string;
  memberCount: number;
  placeCount: number;
  isEnd: boolean;
  createdAt: string | Date;
  modifiedAt: string | Date;
  startDate: string | Date;
  endDate: string | Date;
}
