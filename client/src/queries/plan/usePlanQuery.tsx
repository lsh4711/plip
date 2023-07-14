import { useQuery } from '@tanstack/react-query';

import instance from '../axiosinstance';
import { GetScheduleResponse } from '@/types/api/schedules-types';

const getPlan = (planId: string) =>
  instance
    .get<GetScheduleResponse>(`/api/schedules/${planId}`, {
      withCredentials: true,
    })
    .then((res) => res.data);

const usePlanQuery = (planId: string) => {
  return useQuery({
    queryKey: ['/schedule', planId],
    queryFn: () => getPlan(planId),
    useErrorBoundary: false,
  });
};

export default usePlanQuery;
