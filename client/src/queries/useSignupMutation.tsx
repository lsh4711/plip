import { SignupType } from '@/schema/signupSchema';
import BASE_URL from './BASE_URL';
import { useMutation } from '@tanstack/react-query';
import instance from './axiosinstance';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';

const postSignup = async (signupData: SignupType) => {
  const response = await instance.post(
    '/api/users/signup',
    {
      email: signupData.email,
      password: signupData.password,
      nickname: signupData.nickname,
    },
    {
      withCredentials: true,
    }
  );
  return response;
};

const useSignupMutation = () => {
  const toast = useToast();

  const signupMutation = useMutation({
    mutationFn: (signup: SignupType) => postSignup(signup),
    onSuccess(data, variables, context) {
      toast({
        content: '회원가입에 성공했습니다.',
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
  return signupMutation;
};

export default useSignupMutation;

// 예전 fetch 코드
// export const postSignup = async (signupData: SignupType) => {
//   const parsedSignupData = {
//     email: signupData.email,
//     password: signupData.password,
//     nickname: signupData.nickname,
//   };

//   const response = await fetch(`${BASE_URL}/api/users/signup`, {
//     method: 'POST',
//     body: JSON.stringify(parsedSignupData),
//     // credentials: 'include',
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   return response;
// };
