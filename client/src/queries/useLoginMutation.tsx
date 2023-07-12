import { isSuccessStatus } from '@/utils';
import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import { LoginType } from '@/schema/loginSchema';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';

const postLogin = async (loginData: LoginType) => {
  console.log(loginData);

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
  const ACCESS_TOKEN = response.headers['Authorization'];
  return {
    response,
    ACCESS_TOKEN,
  };
};

const useLoginMutation = () => {
  const toast = useToast();
  const loginMutation = useMutation({
    mutationFn: (loginData: LoginType) => postLogin(loginData),
    onSuccess(data, variables, context) {
      toast({
        content: '로그인에 성공했습니다.',
        type: 'success',
      });
    },
    onError: (error: AxiosError) => {
      switch (error.response?.status) {
        case 400:
          toast({
            content: '400에러 로그인에 실패했습니다.',
            type: 'warning',
          });
          break;
        case 500:
          toast({
            content: '500에러 로그인에 실패했습니다.',
            type: 'warning',
          });
          break;
        default:
          toast({
            content: '로그인에 실패했습니다.',
            type: 'warning',
          });
          break;
      }
    },
  });
  return loginMutation;
};

export default useLoginMutation;
