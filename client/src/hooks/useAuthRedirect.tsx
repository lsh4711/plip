import { Navigate } from 'react-router-dom';
import { getAuthorizationHeader, isValidToken } from '@/utils/auth';

type UseAuthRedirect = () => {
  isRedirect: boolean;
  naviComponent: JSX.Element | null;
};

const createObjectWithComponent = (boolean: boolean, component: JSX.Element | null) => {
  return {
    isRedirect: boolean,
    naviComponent: component,
  };
};

const useAuthRedirect: UseAuthRedirect = () => {
  const auth = getAuthorizationHeader();
  if (isValidToken(auth)) {
    return createObjectWithComponent(true, <Navigate to={'/login'} replace />);
  }
  return createObjectWithComponent(false, null);
};

export default useAuthRedirect;
