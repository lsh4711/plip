import { useMutation } from '@tanstack/react-query';

import useToast from '@/hooks/useToast';
import { ChangeNicknameType } from '@/schema/changeNicknameSchema';
import instance from '../axiosinstance';

const patchUserNickname = ({ nickname }: ChangeNicknameType) =>
  instance.patch('/api/users', {
    nickname,
  });

const useChangeNicknameMutation = () => {
  const toast = useToast();

  return useMutation({
    mutationFn: patchUserNickname,
    onSuccess: () => {
      toast({
        type: 'success',
        content: '닉네임이 변경되었습니다.',
      });
    },
    onError: () => {
      toast({
        type: 'warning',
        content: '닉네임 변경에 실패했습니다.',
      });
    },
  });
};

export default useChangeNicknameMutation;
