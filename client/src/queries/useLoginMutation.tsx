import { isSuccessStatus } from '@/utils';
import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import { LoginType } from '@/schema/loginSchema';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';

// const postLogin = async (loginData: LoginType) => {
//   const response = await fetch(`${BASE_URL}/api/users/login`, {
//     method: 'POST',
//     body: JSON.stringify(loginData),
//     credentials: 'include',
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   const ACCESS_TOKEN = response.headers.get('Authorization');
//   const REFRESH_TOKEN = response.headers.get('Refresh');
//   const status = isSuccessStatus(response);
//   console.log('액세스토큰', ACCESS_TOKEN);
//   console.log('리프레쉬토큰', REFRESH_TOKEN);
//   return {
//     accesstoken: ACCESS_TOKEN,
//     refreshtoken: REFRESH_TOKEN,
//     status: status,
//     response,
//   };
// };

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
