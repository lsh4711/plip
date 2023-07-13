import { ReactComponent as NaverIcon } from '@/assets/icons/naverauth.svg';
import { BASE_URL } from '@/queries';
import Button from '../atom/Button';

const NaverButton = () => {
  const naverOauthLogin = () => {
    window.location.href = `${BASE_URL}/oauth2/authorization/naver`;
  };
  return (
    <Button hovercolor={'default'} hoveropacity={'active'} onClick={() => naverOauthLogin()}>
      <NaverIcon />
    </Button>
  );
};

export default NaverButton;
