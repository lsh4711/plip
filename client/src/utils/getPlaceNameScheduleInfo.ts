import { Schedule } from '@/types/api/schedules-types';

const getPlacNameScheduleInfo = (placeId: number, scheduleInfo: Schedule) => {
  console.log(scheduleInfo);
  const days = scheduleInfo.places.length;

  if (placeId > -1 && days > 0) {
    const schedules = scheduleInfo.places;

    for (let day = 0; day < schedules.length; day++) {
      for (let i = 0; i < schedules[day].length; i++) {
        const schedule = schedules[day][i];

        if (schedule.schedulePlaceId === placeId) {
          // console.log(schedule.name);
          return schedule.name;
        }
      }
    }
  }

  return;
};

export default getPlacNameScheduleInfo;
