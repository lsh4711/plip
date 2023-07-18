import { useQuery } from '@tanstack/react-query';
import instance from '../axiosinstance';

const getAccessTokenAxios = async () => {
  const response = await instance.get('/api/tokens', { withCredentials: true });
  return response;
};

const useGetAccessTokenQuery = () => {
  const getAccessTokenQuery = useQuery({
    queryKey: ['getAccessToken'],
    queryFn: getAccessTokenAxios,
    suspense: true,
    retry: 1,
    enabled: false,
  });

  return getAccessTokenQuery;
};

export default useGetAccessTokenQuery;
