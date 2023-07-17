import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';

const useEmailRequestMutation = (type: 'pw' | 'signup') => {
  const toast = useToast();
  const postEmailRequest = async (email: string) => {
    const response = await instance.post(`/api/mail?type=${type}`, {
      email,
    });
    return response;
  };

  const emailRequest = useMutation({
    mutationFn: (email: string) => postEmailRequest(email),
    onSuccess(data, variables, context) {
      toast({
        content: '이메일 요청이 전송되었습니다.',
        type: 'success',
      });
    },
    onError: (error: AxiosError) => {
      const message =
        typeof error.response?.data === 'string'
          ? error.response.data
          : '이메일 요청 전송에 실패했습니다.';
      toast({
        content: message,
        type: 'warning',
      });
    },
  });

  return emailRequest;
};

export default useEmailRequestMutation;
