import { useMutation } from '@tanstack/react-query';

import useToast from '@/hooks/useToast';
import { ChangePasswordType } from '@/schema/changePasswordSchema';
import instance from '../axiosinstance';

const patchUserPassword = ({ password }: Pick<ChangePasswordType, 'password'>) =>
  instance.patch('/api/users', {
    password,
  });

const useChangePasswordMutation = () => {
  const toast = useToast();

  return useMutation({
    mutationFn: patchUserPassword,
    onSuccess: () => {
      toast({
        type: 'success',
        content: '비밀번호가 변경되었습니다.',
      });
    },
    onError: () => {
      toast({
        type: 'warning',
        content: '비밀번호 변경에 실패했습니다.',
      });
    },
  });
};

export default useChangePasswordMutation;
