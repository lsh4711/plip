import { useQuery } from '@tanstack/react-query';
import ScheduleAPI from '../ScheduleAPI';

const useMyTripQuery = () => {
  const scheduleAPI = new ScheduleAPI();

  //   return useQuery({
  //     queryKey: '/schedules',
  //     queryFn: () => {
  //       scheduleAPI.getAllMySchedules();
  //     },
  //   });
  return useQuery(['/schedules'], scheduleAPI.getAllMySchedules);
};

export default useMyTripQuery;
