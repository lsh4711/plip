import React from 'react';
import Button from '../atom/Button';
import Paragraph from '../atom/Paragraph';
import { Link, useLocation, useParams } from 'react-router-dom';
import KakaoButton from './KakaoButton';
import NaverButton from './NaverButton';

interface OauthUIProps {}

const OauthUI = ({}: OauthUIProps) => {
  const endpoint = useLocation();
  console.log(endpoint);
  return (
    <>
      <div className=" mt-6 flex items-center justify-center gap-x-5">
        <KakaoButton />
        <NaverButton />
      </div>
      <div className=" mt-6 flex flex-col items-center justify-center gap-y-6">
        <Paragraph>
          {endpoint.pathname === '/signup' ? (
            <>
              이미 회원이신가요?{' '}
              <Link to={'/login'} className=" text-blue-500">
                로그인하기
              </Link>
            </>
          ) : (
            <>
              계정이 없으신가요?{' '}
              <Link to={'/signup'} className=" text-blue-500">
                통합 회원가입 하기
              </Link>
              <div className=" mt-3">
                비밀번호를 잊으셨나요?{' '}
                <Link to={'/login/password'} className=" text-blue-500">
                  비밀번호 찾기
                </Link>
              </div>
            </>
          )}
        </Paragraph>
      </div>
    </>
  );
};

export default OauthUI;
