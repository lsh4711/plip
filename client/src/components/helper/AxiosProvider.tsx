import useGetAccessTokenQuery from '@/queries/auth/useGetAccessTokenQuery';
import instance from '@/queries/axiosinstance';
import React from 'react';
import { AxiosError } from 'axios';

interface AxiosProviderProps {
  children: React.ReactNode;
}

const AxiosProvider = ({ children }: AxiosProviderProps) => {
  const getAccesstokenQuery = useGetAccessTokenQuery();
  React.useEffect(() => {
    instance.interceptors.response.use(
      (response) => response,
      async (error: AxiosError) => {
        if (error.response && error.response.status === 401) {
          const originalRequest = error.config;
          await getAccesstokenQuery.refetch();
          return instance(originalRequest!);
        }
        return Promise.reject(error);
      }
    );
  }, []);
  return <>{children}</>;
};

export default AxiosProvider;
