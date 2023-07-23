import { useQuery } from '@tanstack/react-query';

import instance from '../axiosinstance';

interface User {
  data: { email: string; nickname: string };
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
  });
  return inquireUsers;
};

export default useInquireUsersQuery;
