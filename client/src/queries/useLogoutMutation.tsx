import { useDispatch } from 'react-redux';
import instance from './axiosinstance';
import { useMutation, useQuery } from '@tanstack/react-query';
import useToast from '@/hooks/useToast';

const useLogoutMutation = () => {
  const toast = useToast();
  const postLogout = async () => {
    const response = await instance.post('/api/users/logout', null, {
      withCredentials: true,
    });
    return response;
  };

  const logoutMutation = useMutation({
    mutationFn: postLogout,
    onSuccess: () => toast({ content: '로그아웃에 성공했습니다.', type: 'success' }),
    onError: () => toast({ content: '로그아웃에 실패했습니다.', type: 'warning' }),
  });
  return logoutMutation;
};

export default useLogoutMutation;
