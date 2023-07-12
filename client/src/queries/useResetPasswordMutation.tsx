import { ResetPasswordType } from '@/schema/resetPasswordSchema';
import instance from './axiosinstance';
import { useMutation } from '@tanstack/react-query';
import useToast from '@/hooks/useToast';

const patchResetPassword = async ({ username, password }: ResetPasswordType) => {
  const response = await instance.patch(
    '/api/users/password',
    {
      email: username,
      password: password,
    },
    { withCredentials: true }
  );
};

const useResetPasswordMutation = () => {
  const toast = useToast();
  const resetPasswordMutation = useMutation({
    mutationFn: (formData: ResetPasswordType) => patchResetPassword(formData),
    onSuccess: () => {
      toast({ content: '성공적으로 로그아웃했습니다.', type: 'success' });
    },
    onError: () => {
      toast({ content: '로그아웃에 실패했습니다.', type: 'warning' });
    },
  });

  return resetPasswordMutation;
};

export default useResetPasswordMutation;
