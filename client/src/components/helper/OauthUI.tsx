import React from 'react';
import Button from '../atom/Button';
import Paragraph from '../atom/Paragraph';
import { ReactComponent as KakaoIcon } from '@/assets/icons/kakaoauth.svg';
import { ReactComponent as NaverIcon } from '@/assets/icons/naverauth.svg';

interface OauthUIProps {}

const OauthUI = ({}: OauthUIProps) => {
  return (
    <>
      <div className=" mt-6 flex items-center justify-center gap-x-5">
        <Button hovercolor={'default'} hoveropacity={'active'}>
          <KakaoIcon />
        </Button>
        <Button hovercolor={'default'} hoveropacity={'active'}>
          <NaverIcon />
        </Button>
      </div>
      <div className=" my-6 flex flex-col items-center justify-center gap-y-6">
        <Paragraph>
          이미 회원이신가요?{' '}
          <a href="/" className=" text-blue-500">
            로그인하기
          </a>
        </Paragraph>
      </div>
    </>
  );
};

export default OauthUI;
