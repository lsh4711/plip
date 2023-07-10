import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import useTypeSelector from './useTypeSelector';
import instance from '@/queries/axiosinstance';
import { setAccessToken } from '@/redux/slices/authSlice';

const useInstance = () => {
  const dispatch = useDispatch();
  const selector = useTypeSelector();

  useEffect(() => {
    instance.interceptors.request.use((config) => {
      const accesstoken = selector.auth.accesstoken;
      // if (accesstoken) {
      //   config.headers['Authorization'] = accesstoken;
      // }
      dispatch(setAccessToken({ accesstoken: '어엄준식' }));
      console.log('인터셉팅하고있어염', accesstoken);
      return config;
    });

    instance.interceptors.response.use((response) => {
      console.log(response);
      console.log('어엄응답도인터셉팅');
      return response;
    });
  }, []);

  return;
};

export default useInstance;
