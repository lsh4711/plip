import React from 'react';
import setAccessTokenToHeader from '@/utils/auth/setAccesstokenToHeader';
import { useNavigate } from 'react-router';
import { useSearchParams } from 'react-router-dom';
import { EMPTY_TOKEN } from '@/datas/constants';
import { KAKAO_OAUTH_ACCESS_TOKEN } from '@/datas/constants';
import useSetAccessToken from '@/hooks/useSetAccessToken';
const getAccesstokenToQueryString = (callback: URLSearchParams) => {
  const token = callback.get(KAKAO_OAUTH_ACCESS_TOKEN);
  if (typeof token !== 'string') return EMPTY_TOKEN;
  return token;
};

const KakaoRedirect = () => {
  const navigate = useNavigate();
  const [querystring] = useSearchParams();
  const token = getAccesstokenToQueryString(querystring);
  const accesstokenSetter = useSetAccessToken();
  setAccessTokenToHeader(token);
  return <div></div>;
};

export default KakaoRedirect;
