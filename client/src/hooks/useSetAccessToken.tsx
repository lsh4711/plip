import { AppDispatch } from '@/redux/store';
import { AccessTokenType, setAccessToken } from '@/redux/slices/authSlice';
import { useDispatch } from 'react-redux';

const useSetAccessToken = () => {
  const dispatch = useDispatch<AppDispatch>();

  return (token: AccessTokenType) => {
    dispatch(setAccessToken(token));
  };
};

export default useSetAccessToken;
