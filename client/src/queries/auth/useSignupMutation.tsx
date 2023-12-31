import { SignupType } from '@/schema/signupSchema';
import { useMutation } from '@tanstack/react-query';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';
import { useNavigate } from 'react-router-dom';
import instance from '../axiosinstance';

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
