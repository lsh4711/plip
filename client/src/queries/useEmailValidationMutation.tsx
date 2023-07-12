import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import useToast from '@/hooks/useToast';

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
    onError: (error: Error) => {
      toast({
        content: '인증에 실패했습니다.',
        type: 'warning',
      });
    },
  });
  return emailValidation;
};

export default useEmailValidationMutation;

// 예전 fetch 코드
// const postEmailValidation = async ({ email, authcode }: EmailValidationType) => {
//   const response = await fetch(`${BASE_URL}/api/mail/auth`, {
//     method: 'POST',
//     body: JSON.stringify({ email, authCode: authcode }),
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   return response;
// };
