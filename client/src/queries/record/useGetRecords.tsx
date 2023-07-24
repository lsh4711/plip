import { useQuery } from '@tanstack/react-query';
import instance from '../axiosinstance';
import { useDispatch } from 'react-redux';
import { GetScheduleResponse } from '@/types/api/schedules-types';
import { setSchedule } from '@/redux/slices/scheduleSlice';
import { setRecords } from '@/redux/slices/recordsSlice';

const getRecordMap = (scheduleId: string) => {
  return instance.get<GetScheduleResponse>(`/api/schedules/${scheduleId}`).then((res) => res.data);
};

const useGetRecords = (scheduleId: string) => {
  const dispatch = useDispatch();

  return useQuery({
    queryKey: ['/records', scheduleId],
    queryFn: () =>
      getRecordMap(scheduleId).then((data) => {
        console.log(data);
        dispatch(setRecords(data.recordsMap));
        dispatch(setSchedule(data.schedule.places));
        return data.schedule;
      }),
  });
};

export default useGetRecords;
