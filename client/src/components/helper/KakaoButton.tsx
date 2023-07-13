import { ReactComponent as KakaoIcon } from '@/assets/icons/kakaoauth.svg';
import { BASE_URL } from '@/queries';
import Button from '../atom/Button';

const KakaoButton = () => {
  const kakaoOauthLogin = () => {
    window.location.href = `${BASE_URL}/oauth2/authorization/kakao`;
  };
  return (
    <Button hovercolor={'default'} hoveropacity={'active'} onClick={() => kakaoOauthLogin()}>
      <KakaoIcon />
    </Button>
  );
};

export default KakaoButton;
