import { SignupType } from '@/schema/signupSchema';
import BASE_URL from './BASE_URL';
import { useMutation } from '@tanstack/react-query';
import instance from './axiosinstance';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';
import { useNavigate } from 'react-router-dom';

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
  const navigate = useNavigate();
  const signupMutation = useMutation({
    mutationFn: (signup: SignupType) => postSignup(signup),
    onSuccess(data, variables, context) {
      navigate('/login');
      toast({
        content: '회원가입에 성공했습니다.',
        type: 'success',
      });
    },
    onError: (error: AxiosError) => {
      const message =
        typeof error.response?.data === 'string' ? error.response.data : '회원가입에 실패했습니다.';
      toast({
        content: message,
        type: 'warning',
      });
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
