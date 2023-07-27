import { useQuery } from '@tanstack/react-query';

import instance from './axiosinstance';
import { AxiosError } from 'axios';

type EventQueryType = {
  win: boolean;
  ranking: number;
  giftCodeImage: string;
  nickname: string;
  message: string;
};

const getEvent = () =>
  instance.get<EventQueryType>('/events/gifts', { withCredentials: true }).then((res) => res.data);

const useEventQuery = () => {
  return useQuery<EventQueryType, AxiosError<string>>({
    queryKey: ['/Event'],
    queryFn: getEvent,
    useErrorBoundary: false,
  });
};

export default useEventQuery;
