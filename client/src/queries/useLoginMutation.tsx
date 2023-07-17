import { useMutation } from '@tanstack/react-query';
import instance from './axiosinstance';
import { LoginType } from '@/schema/loginSchema';
import useToast from '@/hooks/useToast';
import useInquireUsersQuery from './useInquireUsersQuery';
import { useNavigate } from 'react-router-dom';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import useSetAccessToken from '@/hooks/useSetAccessToken';
import useSuccessFailToast from '@/hooks/useSuccessFailToast';

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
  const inquireQuery = useInquireUsersQuery();
  const accessTokenSetter = useSetAccessToken();
  const mutateHandler = useSuccessFailToast();

  const loginMutation = useMutation({
    mutationFn: (loginData: LoginType) => postLogin(loginData),
    onSuccess(data, variables, context) {
      navigate('/');
      accessTokenSetter({ accesstoken: data.ACCESS_TOKEN });
      inquireQuery.refetch().then((data) => {
        toast({
          content: '로그인에 성공했습니다.',
          type: 'success',
        });

        inquireQuery.refetch();

        return data;
      });
    },
    onError: mutateHandler.onError('로그인에 실패했습니다.'),
  });
  return loginMutation;
};

export default useLoginMutation;
