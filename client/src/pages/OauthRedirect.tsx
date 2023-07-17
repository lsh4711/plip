import React from 'react';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import { useNavigate } from 'react-router';
import { useSearchParams } from 'react-router-dom';
import { EMPTY_TOKEN } from '@/datas/constants';
import { KAKAO_OAUTH_ACCESS_TOKEN } from '@/datas/constants';
import useSetAccessToken from '@/hooks/useSetAccessToken';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
const getAccesstokenToQueryString = (callback: URLSearchParams) => {
  const token = callback.get(KAKAO_OAUTH_ACCESS_TOKEN);
  if (typeof token !== 'string') return EMPTY_TOKEN;
  return token;
};

const OauthRedirect = () => {
  const navigate = useNavigate();
  const inquireQuery = useInquireUsersQuery();
  const [querystring] = useSearchParams();
  const token = getAccesstokenToQueryString(querystring);
  const dispatchAccesstoken = useSetAccessToken();
  setAccessTokenToHeader(token);
  inquireQuery.refetch();
  dispatchAccesstoken({ accesstoken: token });
  navigate('/');
  return <div>oauth 리다이렉트</div>;
};

export default OauthRedirect;
