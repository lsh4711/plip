import { AppDispatch } from '@/redux/store';
import { AccessTokenType, setAccessToken } from '@/redux/slices/authSlice';
import { useDispatch } from 'react-redux';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';

const useSetAccessToken = () => {
  const dispatch = useDispatch<AppDispatch>();

  return (token: AccessTokenType) => {
    setAccessTokenToHeader(token.accesstoken);
    dispatch(setAccessToken(token));
  };
};

export default useSetAccessToken;
