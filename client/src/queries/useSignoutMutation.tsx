import React from 'react';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';
import { useMutation } from '@tanstack/react-query';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setLogout } from '@/redux/slices/authSlice';
import useToast from '@/hooks/useToast';

const deleteSignout = async () => {
  const response = await instance.delete('api/users', {
    withCredentials: true,
  });
  return response;
};

const useSignoutMutation = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const toast = useToast();
  const signoutMutation = useMutation({
    mutationFn: deleteSignout,
    onSuccess: () => {
      dispatch(setLogout());
      navigate('/');
      toast({ content: '회원탈퇴에 성공하셨습니다.', type: 'success' });
    },
  });
  return signoutMutation;
};

export default useSignoutMutation;
