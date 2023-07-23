import { useQuery } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';

import { setSchedule } from '@/redux/slices/scheduleSlice';
import axios from 'axios';
import BASE_URL from '../BASE_URL';

export type RequestShareProps = {
  planId: string;
  userId: string;
  userEmail: string;
};

const getPlan = ({ planId, userId, userEmail }: RequestShareProps) => {
  return axios
    .get(`${BASE_URL}/api/schedules/${planId}/share?id=${userId}&code=${userEmail}`)
    .then((res) => res.data);
};

const useSharePlanQuery = ({ planId, userId, userEmail }: RequestShareProps) => {
  const dispatch = useDispatch();

  return useQuery({
    queryKey: ['/schedule/share', planId, userId, userEmail],
    queryFn: () =>
      getPlan({ planId, userId, userEmail }).then((data) => {
        dispatch(setSchedule(data.places));
        return data;
      }),
  });
};

export default useSharePlanQuery;
