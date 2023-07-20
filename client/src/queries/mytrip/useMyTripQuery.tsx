import { useQuery } from '@tanstack/react-query';
import ScheduleAPI from '../ScheduleAPI';

const useMyTripQuery = () => {
  const scheduleAPI = new ScheduleAPI();

  return useQuery(['/schedules'], scheduleAPI.onGetAllSchedules, { useErrorBoundary: false });
};

export default useMyTripQuery;
