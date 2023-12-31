import { useMutation } from '@tanstack/react-query';
import { LoginType } from '@/schema/loginSchema';
import { getFCMToken } from '@/utils/fcm';

import useToast from '@/hooks/useToast';
import { useNavigate } from 'react-router-dom';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import useSetAccessToken from '@/hooks/useSetAccessToken';
import useSuccessFailToast from '@/hooks/useSuccessFailToast';
import instance from '../axiosinstance';
import useInquireUsersQuery from './useInquireUsersQuery';

const postLogin = async (loginData: LoginType) => {
  const response = await instance.post(
    '/api/users/login',
    {
      username: loginData.username,
      password: loginData.password,
    },
    {
      withCredentials: true,
    }
  );
  const ACCESS_TOKEN = response.headers['authorization'];
  setAccessTokenToHeader(ACCESS_TOKEN);

  return {
    response,
    ACCESS_TOKEN,
  };
};

const useLoginMutation = () => {
  const toast = useToast();
  const navigate = useNavigate();
  const dispatchAccesstoken = useSetAccessToken();
  const mutateHandler = useSuccessFailToast();
  const inquireQuery = useInquireUsersQuery();

  const loginMutation = useMutation({
    mutationFn: (loginData: LoginType) => postLogin(loginData),
    onSuccess(data, variables, context) {
      navigate('/');
      inquireQuery.refetch().then(() => inquireQuery.refetch());
      dispatchAccesstoken({ accesstoken: data.ACCESS_TOKEN });
      getFCMToken();

      toast({
        content: '로그인에 성공했습니다.',
        type: 'success',
      });
    },
    onError: mutateHandler.onError('로그인에 실패했습니다.'),
  });
  return loginMutation;
};

export default useLoginMutation;
