import React, { useEffect, useState } from 'react';
import CopyToClipboard from 'react-copy-to-clipboard';
import Button from '../atom/Button';
import { ReactComponent as KakaoIcon } from '@/assets/icons/kakaoauth.svg';
import { ReactComponent as ShareIcon } from '@/assets/icons/share-link.svg';
import useToast from '@/hooks/useToast';
import { UserGetRequest } from '@/types/api/users-types';

const SharesButtons = ({
  scheduleId,
  userInfo,
}: {
  scheduleId: number;
  userInfo: UserGetRequest;
}) => {
  const [shareLink, setShareLink] = useState('');
  const toast = useToast();

  useEffect(() => {
    const { memberId, email } = userInfo;

    // 이후 배포주소로 변경 필요
    setShareLink(
      `https://plip.netlify.app/plan/detail/${scheduleId}/share?id=${memberId}&email=${email}`
      // `http://localhost:5173/plan/detail/${scheduleId}/share?id=${memberId}&email=${email}`
    );
  }, []);

  return (
    <>
      <CopyToClipboard
        text={shareLink}
        onCopy={() => toast({ content: '링크 복사 완료!', type: 'success' })}
      >
        <Button
          hovercolor={'default'}
          variant={'ring'}
          className="h-[50px] w-[50px] rounded-[100%] hover:scale-110 hover:transition-all"
        >
          <ShareIcon fill="#4568DC" />
        </Button>
      </CopyToClipboard>

      <Button
        hovercolor={'default'}
        className="h-[50px] w-[50px] p-0 hover:scale-110 hover:transition-all"
      >
        <KakaoIcon />
      </Button>
    </>
  );
};

export default SharesButtons;
