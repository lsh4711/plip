import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import instance from '@/queries/axiosinstance';
import { EMPTY_TOKEN, setAccessToken } from '@/redux/slices/authSlice';
import { RootState } from '@/redux/store';

const useInstance = () => {
  const dispatch = useDispatch();
  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);

  if (accesstoken !== null && accesstoken !== EMPTY_TOKEN) {
    instance.defaults.headers.common['Authorization'] = accesstoken;
  }

  useEffect(() => {
    instance.interceptors.response.use((response) => {
      let newToken;
      if (accesstoken) {
        newToken = response.headers['authorization'];
      }

      dispatch(setAccessToken({ accesstoken: newToken }));
      return response;
    });
  }, [accesstoken]);

  return;
};

export default useInstance;
