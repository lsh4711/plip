import { useQuery } from '@tanstack/react-query';
import instance from './axiosinstance';
import { EMPTY_TOKEN } from '@/redux/slices/authSlice';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';

interface User {
  data: { nickname: string };
}

const getUsers = async () => {
  const response = await instance.get<User>('/api/users', {
    withCredentials: true,
  });
  return response;
};

const useInquireUsersQuery = () => {
  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);
  const inquireUsers = useQuery({
    queryKey: ['users', accesstoken],
    queryFn: getUsers,
    suspense: true,
    retry: 3,
    enabled: false,
    staleTime: 3 * 60 * 1000,
  });
  return inquireUsers;
};

export default useInquireUsersQuery;
