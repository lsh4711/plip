import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import { Navigate } from 'react-router';
import { useSearchParams } from 'react-router-dom';
import { EMPTY_TOKEN } from '@/datas/constants';
import { KAKAO_OAUTH_ACCESS_TOKEN } from '@/datas/constants';
import useSetAccessToken from '@/hooks/useSetAccessToken';
import addBearer from '@/utils/auth/addBearer';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import { getFCMToken } from '@/utils/fcm';
import { getMessaging } from 'firebase/messaging';

const getAccesstokenToQueryString = (callback: URLSearchParams) => {
  const token = callback.get(KAKAO_OAUTH_ACCESS_TOKEN);

  if (typeof token !== 'string') return EMPTY_TOKEN;

  return token;
};

const OauthRedirect = () => {
  const inquireQuery = useInquireUsersQuery();
  const [querystring] = useSearchParams();
  const token = addBearer(getAccesstokenToQueryString(querystring));
  const dispatchAccesstoken = useSetAccessToken();
  setAccessTokenToHeader(token);
  dispatchAccesstoken({ accesstoken: token });
  getFCMToken();

  inquireQuery.refetch();

  return <Navigate to={'/'} replace />;
};

export default OauthRedirect;
