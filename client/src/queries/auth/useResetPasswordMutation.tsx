import { ResetPasswordType } from '@/schema/resetPasswordSchema';
import { useMutation } from '@tanstack/react-query';
import useToast from '@/hooks/useToast';
import { useNavigate } from 'react-router-dom';
import instance from '../axiosinstance';

const patchResetPassword = async ({ email, password }: ResetPasswordType) => {
  const response = await instance.patch(
    '/api/users/password',
    {
      email: email,
      password: password,
    },
    { withCredentials: true }
  );
  return response;
};

const useResetPasswordMutation = () => {
  const toast = useToast();
  const navigate = useNavigate();
  const resetPasswordMutation = useMutation({
    mutationFn: (formData: ResetPasswordType) => patchResetPassword(formData),
    onSuccess: () => {
      navigate('/login');
      toast({ content: '비밀번호를 재설정했습니다.', type: 'success' });
    },
    onError: () => {
      toast({ content: '비밀번호 재설정에 실패했습니다.', type: 'warning' });
    },
  });

  return resetPasswordMutation;
};

export default useResetPasswordMutation;
