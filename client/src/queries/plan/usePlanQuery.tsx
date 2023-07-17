import { useQuery } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';

import { setSchedule } from '@/redux/slices/scheduleSlice';
import { GetScheduleResponse } from '@/types/api/schedules-types';
import instance from '../axiosinstance';

const getPlan = (planId: string) =>
  instance
    .get<GetScheduleResponse>(`/api/schedules/${planId}`, {
      withCredentials: true,
    })
    .then((res) => res.data);

const usePlanQuery = (planId: string) => {
  const dispatch = useDispatch();

  return useQuery({
    queryKey: ['/schedule', planId],
    queryFn: () =>
      getPlan(planId).then((data) => {
        dispatch(setSchedule(data.places));
        return data;
      }),
    useErrorBoundary: false,
    refetchOnWindowFocus: false,
  });
};

export default usePlanQuery;
