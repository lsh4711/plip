import { useMutation } from '@tanstack/react-query';
import useToast from '@/hooks/useToast';
import useSuccessFailToast from '@/hooks/useSuccessFailToast';
import instance from '../axiosinstance';

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
  const mutationHandler = useSuccessFailToast();
  const toast = useToast();

  const emailValidation = useMutation({
    mutationFn: (emailObj: EmailValidationType) => postEmailValidation(emailObj),
    onSuccess(data, variables, context) {
      toast({
        content: '인증에 성공했습니다.',
        type: 'success',
      });
    },
    onError: mutationHandler.onError('인증에 실패했습니다.'),
  });
  return emailValidation;
};

export default useEmailValidationMutation;
