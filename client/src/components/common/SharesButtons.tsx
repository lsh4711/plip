import { useEffect, useState } from 'react';
import CopyToClipboard from 'react-copy-to-clipboard';
import { ReactComponent as KakaoIcon } from '@/assets/icons/kakaoauth.svg';
import { ReactComponent as ShareIcon } from '@/assets/icons/share-link.svg';
import useToast from '@/hooks/useToast';
import instance from '@/queries/axiosinstance';
import { UserGetRequest } from '@/types/api/users-types';
import RoundButton from './RoundButton';

const SharesButtons = ({ scheduleId }: { scheduleId: number }) => {
  const [shareLink, setShareLink] = useState('');
  const [userInfo, setUserInfo] = useState<UserGetRequest>();
  const toast = useToast();

  useEffect(() => {
    const getHashedShareLink = async () => {
      await instance.get(`/api/schedules/${scheduleId}/share/link `).then((res) => {
        console.log(res.data);
        setShareLink(res.data);
      });
    };

    getHashedShareLink();

    // 이후 배포주소로 변경 필요
  }, []);

  return (
    <>
      <CopyToClipboard
        text={shareLink}
        onCopy={() => toast({ content: '링크 복사 완료!', type: 'success' })}
      >
        <RoundButton>
          <ShareIcon fill="#4568DC" />
        </RoundButton>
      </CopyToClipboard>

      <RoundButton onClick={() => alert('준비중입니당!!!')}>
        <KakaoIcon />
      </RoundButton>
    </>
  );
};

export default SharesButtons;
