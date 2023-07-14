import instance from '@/queries/axiosinstance';
import { EMPTY_TOKEN } from '@/redux/slices/authSlice';
import { Navigate } from 'react-router-dom';

type UseAuthRedirect = () => {
  isRedirect: boolean;
  naviComponent: JSX.Element | null;
};

const useAuthRedirect: UseAuthRedirect = () => {
  const auth = instance.defaults.headers['Authorization'];
  if (auth === EMPTY_TOKEN || auth === undefined || auth === null || !auth)
    return {
      isRedirect: true,
      naviComponent: <Navigate to={'/login'} />,
    };

  return {
    isRedirect: false,
    naviComponent: null,
  };
};

export default useAuthRedirect;
