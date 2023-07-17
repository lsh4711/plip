// 나의 일정
export interface MyTripTypes {
  scheduleId: string;
  title: string;
  memberCount: number;
  placeCount: number;
  isEnd: boolean;
  createdAt: Date;
  modifiedAt: Date;
  startDate: Date;
  endDate: Date;
}
