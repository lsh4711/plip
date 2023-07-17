import { EMPTY_TOKEN } from '@/datas/constants';
import instance from '@/queries/axiosinstance';

import { AxiosHeaderValue } from 'axios';
import { Navigate } from 'react-router-dom';

type UseAuthRedirect = () => {
  isRedirect: boolean;
  naviComponent: JSX.Element | null;
};

const getAuthorizationHeader = () => instance.defaults.headers['Authorization'];

const isValidToken = (token: AxiosHeaderValue) => {
  return token === EMPTY_TOKEN || token === undefined || token === null || !token;
};

const createObjectWithComponent = (boolean: boolean, component: JSX.Element | null) => {
  return {
    isRedirect: boolean,
    naviComponent: component,
  };
};

const useAuthRedirect: UseAuthRedirect = () => {
  const auth = getAuthorizationHeader();
  console.log(auth);
  console.log(isValidToken(auth));
  if (isValidToken(auth)) {
    return createObjectWithComponent(true, <Navigate to={'/login'} replace />);
  }
  return createObjectWithComponent(false, null);
};

export default useAuthRedirect;
