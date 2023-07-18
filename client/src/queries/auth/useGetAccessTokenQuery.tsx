import { useQuery, useQueryClient } from '@tanstack/react-query';
import instance from '../axiosinstance';
import { getAuthorizationHeader, isValidToken } from '@/utils/auth';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import useInquireUsersQuery from './useInquireUsersQuery';
import useSetAccessToken from '@/hooks/useSetAccessToken';
import loginLocalStorage from '@/utils/auth/localstorage';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';

const getAccessTokenAxios = async () => {
  console.log('실행되나요?');

  const response = await instance.get('/api/tokens', { withCredentials: true });
  const ACCESS_TOKEN = response.headers['authorization'];
  setAccessTokenToHeader(ACCESS_TOKEN);
  return response;
};

const useGetAccessTokenQuery = () => {
  const inquireQuery = useInquireUsersQuery();
  const dispatchAccessToken = useSetAccessToken();
  const wasLogin = loginLocalStorage.getWasLoginFromLocalStorage;
  const isLogin = useSelector((state: RootState) => state.auth.isLogin);
  const getAccessTokenQuery = useQuery({
    queryKey: ['getAccessToken'],
    queryFn: getAccessTokenAxios,
    suspense: true,
    retry: 1,
    enabled: wasLogin && !isLogin,
    staleTime: 1000 * 60 * 10,
    onSuccess: (response) => {
      console.log('온석섹슥');
      inquireQuery.refetch().then(() => inquireQuery.refetch());
      dispatchAccessToken({ accesstoken: response.headers['authorization'] });
    },
  });

  return getAccessTokenQuery;
};

export default useGetAccessTokenQuery;
