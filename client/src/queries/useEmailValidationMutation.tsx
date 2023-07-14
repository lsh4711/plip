import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';

interface EmailValidationType {
  email: string;
  authcode: string;
}

const postEmailValidation = async ({ email, authcode }: EmailValidationType) => {
  const response = await instance.post('/api/mail/auth', {
    email,
    authCode: authcode,
  });
  return response;
};

const useEmailValidationMutation = () => {
  const toast = useToast();

  const emailValidation = useMutation({
    mutationFn: (emailObj: EmailValidationType) => postEmailValidation(emailObj),
    onSuccess(data, variables, context) {
      toast({
        content: '인증에 성공했습니다.',
        type: 'success',
      });
    },
    onError: (error: AxiosError) => {
      const message =
        typeof error.response?.data === 'string'
          ? error.response.data
          : '이메일 인증에 실패했습니다.';
      toast({
        content: message,
        type: 'warning',
      });
    },
  });
  return emailValidation;
};

export default useEmailValidationMutation;
