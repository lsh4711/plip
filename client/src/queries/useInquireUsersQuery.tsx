import { useQuery } from '@tanstack/react-query';
import instance from './axiosinstance';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { EMPTY_TOKEN } from '@/redux/slices/authSlice';

interface User {
  data: { nickname: string };
}

const useInquireUsersQuery = () => {
  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);
  const getUsers = async () => {
    const response = await instance.get<User>('/api/users', {
      withCredentials: true,
    });
    return response;
  };

  const inquireUsers = useQuery({
    queryKey: ['users', accesstoken],
    queryFn: getUsers,
    suspense: true,
    retry: 3,
    enabled: false,
  });
  return inquireUsers;
};

export default useInquireUsersQuery;
