import { useQuery, useQueryClient } from '@tanstack/react-query';
import instance from '../axiosinstance';
import { getAuthorizationHeader, isValidToken } from '@/utils/auth';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import useInquireUsersQuery from './useInquireUsersQuery';
import useSetAccessToken from '@/hooks/useSetAccessToken';

const getAccessTokenAxios = async () => {
  const response = await instance.get('/api/tokens', { withCredentials: true });
  const ACCESS_TOKEN = response.headers['authorization'];
  setAccessTokenToHeader(ACCESS_TOKEN);
  return response;
};

const useGetAccessTokenQuery = () => {
  const inquireQuery = useInquireUsersQuery();
  const dispatchAccessToken = useSetAccessToken();

  const getAccessTokenQuery = useQuery({
    queryKey: ['getAccessToken'],
    queryFn: getAccessTokenAxios,
    suspense: true,
    retry: 1,
    enabled: !isValidToken(getAuthorizationHeader()),
    staleTime: 1000 * 60 * 10,
    onSuccess: (response) => {
      inquireQuery.refetch();
      dispatchAccessToken({ accesstoken: response.headers['authorization'] });
    },
  });

  return getAccessTokenQuery;
};

export default useGetAccessTokenQuery;
