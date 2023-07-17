import { useDispatch } from 'react-redux';
import instance from './axiosinstance';
import { useMutation, useQuery } from '@tanstack/react-query';
import useToast from '@/hooks/useToast';
import { AxiosError } from 'axios';
import { AppDispatch } from '@/redux/store';
import { setLogout } from '@/redux/slices/authSlice';
import { useNavigate } from 'react-router-dom';
import useSuccessFailToast from '@/hooks/useSuccessFailToast';

const postLogout = async () => {
  const response = await instance.post('/api/users/logout', null, {
    withCredentials: true,
  });
  return response;
};

const useLogoutMutation = () => {
  const toast = useToast();
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const mutationHandler = useSuccessFailToast();

  const logoutMutation = useMutation({
    mutationFn: postLogout,
    onSuccess: mutationHandler.onSuccess('로그아웃에 성공했습니다.', () => {
      dispatch(setLogout());
      navigate('/');
    }),
    onError: mutationHandler.onError('로그아웃에 실패했습니다.'),
  });
  return logoutMutation;
};

export default useLogoutMutation;
