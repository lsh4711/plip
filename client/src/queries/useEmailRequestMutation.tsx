import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import useToast from '@/hooks/useToast';

const useEmailRequestMutation = (type: 'pw' | 'signup') => {
  const toast = useToast();
  const postEmailRequest = async (email: string) => {
    const response = await instance.post(`/api/mail?type=${type}/signup`, {
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
    onError: (error: Error) => {
      toast({
        content: '이메일 요청 전송에 실패했습니다.',
        type: 'warning',
      });
    },
  });

  return emailRequest;
};

export default useEmailRequestMutation;

// 예전 fetch 코드
// const postEmailRequest = async (email: string) => {
//   console.log(JSON.stringify({ email: email }));
//   const response = await fetch(`${BASE_URL}/api/mail/signup`, {
//     method: 'POST',
//     body: JSON.stringify({ email: email }),
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   return response;
// };
