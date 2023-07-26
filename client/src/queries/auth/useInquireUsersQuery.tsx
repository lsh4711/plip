import { useQuery } from '@tanstack/react-query';

import instance from '../axiosinstance';
import { UserGetRequest } from '@/types/api/users-types';

interface User {
  data: Omit<UserGetRequest, 'memberId'>;
}

const useInquireUsersQuery = () => {
  const getUsers = async () => {
    const response = await instance.get<User>('/api/users', {
      withCredentials: true,
    });
    return response;
  };

  const inquireUsers = useQuery({
    queryKey: ['users'],
    queryFn: getUsers,
    suspense: true,
    retry: 3,
    enabled: false,
    select: (data) => data.data.data,
  });
  return inquireUsers;
};

export default useInquireUsersQuery;
