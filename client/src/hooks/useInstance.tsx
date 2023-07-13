import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import instance from '@/queries/axiosinstance';
import { EMPTY_TOKEN, setAccessToken } from '@/redux/slices/authSlice';
import { RootState } from '@/redux/store';

const useInstance = () => {
  const dispatch = useDispatch();
  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);
  useEffect(() => {
    instance.interceptors.request.use((config) => {
      config.headers['test'] = 'testheader';
      console.log(accesstoken);
      if (accesstoken !== null && accesstoken !== EMPTY_TOKEN) {
        config.headers['Authorization'] = accesstoken;
      }
      return config;
    });

    instance.interceptors.response.use((response) => {
      const accesstoken = response.headers['authorization'];
      dispatch(setAccessToken({ accesstoken: accesstoken }));
      return response;
    });
  }, [accesstoken]);

  return;
};

export default useInstance;
