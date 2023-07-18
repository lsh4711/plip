import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import { Navigate } from 'react-router';
import { useSearchParams } from 'react-router-dom';
import { EMPTY_TOKEN } from '@/datas/constants';
import { KAKAO_OAUTH_ACCESS_TOKEN } from '@/datas/constants';
import useSetAccessToken from '@/hooks/useSetAccessToken';
import addBearer from '@/utils/auth/addBearer';
const getAccesstokenToQueryString = (callback: URLSearchParams) => {
  const token = callback.get(KAKAO_OAUTH_ACCESS_TOKEN);
  if (typeof token !== 'string') return EMPTY_TOKEN;
  return token;
};

const OauthRedirect = () => {
  const [querystring] = useSearchParams();
  const token = addBearer(getAccesstokenToQueryString(querystring));
  const dispatchAccesstoken = useSetAccessToken();
  setAccessTokenToHeader(token);
  dispatchAccesstoken({ accesstoken: token });

  return <Navigate to={'/'} replace />;
};

export default OauthRedirect;
