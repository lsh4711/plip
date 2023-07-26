import { useMutation, useQueryClient } from '@tanstack/react-query';

import useToast from '@/hooks/useToast';
import { EditUserType } from '@/schema/editUserSchema';
import instance from '../axiosinstance';

const patchUserInfo = ({ nickname, password }: Pick<EditUserType, 'nickname' | 'password'>) => {
  return instance.patch('/api/users', {
    nickname,
    password,
  });
};

const useEditUserMutation = () => {
  const toast = useToast();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: patchUserInfo,
    onSuccess: () => {
      toast({
        content: '회원 정보가 변경되었습니다.',
      });
      // queryClient.invalidateQueries(['users']);
    },
    onError: () => {
      toast({
        content: '회원 정보가 변경에 실패했습니다.',
      });
    },
  });
};

export default useEditUserMutation;