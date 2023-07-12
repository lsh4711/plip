import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import instance from '@/queries/axiosinstance';
import { setAccessToken } from '@/redux/slices/authSlice';
import { RootState } from '@/redux/store';

const useInstance = () => {
  const dispatch = useDispatch();
  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);

  useEffect(() => {
    instance.interceptors.request.use((config) => {
      // if (accesstoken !== null && accesstoken !== '') {
      //   config.headers['Authorization'] = accesstoken;
      // }
      return config;
    });

    instance.interceptors.response.use((response) => {
      console.log(response);
      return response;
    });
  }, [accesstoken]);

  return;
};

export default useInstance;
