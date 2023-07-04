import { ReactComponent as ArrowDownIcon } from '../../assets/icons/arrow-down.svg';
import { ReactComponent as MypageIcon } from '../../assets/icons/mypage.svg';
import { ReactComponent as NotifyIcon } from '../../assets/icons/notification.svg';
import { ReactComponent as ProfileIcon } from '../../assets/icons/profile.svg';
import { ReactComponent as LogoIcon } from '../../assets/logo.svg';

import Button from '../atom/Button';

interface HeaderProps {}

const BeforeLogin = () => {
  return (
    <>
      <Button>Sigin in</Button>
      <Button variant={'primary'} className="font-normal">
        Sigin up
      </Button>
    </>
  );
};

const AfterLogin = () => {
  const username = '유보검'; // 임시 변수
  return (
    <>
      <MypageIcon />
      <NotifyIcon />
      <ProfileIcon />
      <div className="align-center flex text-xl">
        {username} 님
        <ArrowDownIcon width={24} height={24} />
      </div>
    </>
  );
};

const Header = ({}: HeaderProps) => {
  const isLogin = false; // 로그인 상태 구현전 임시 변수입니다.

  return (
    <header className=" h-[80px] w-full border border-b-2">
      <nav className="m-auto flex h-full w-[90%] items-center justify-between">
        <div className="flex items-center gap-2">
          <LogoIcon width={35} height={35} />
          <span className="gradient-text text-xl font-bold">PliP</span>
        </div>
        <div className="flex items-center gap-4">{isLogin ? <AfterLogin /> : <BeforeLogin />}</div>
      </nav>
    </header>
  );
};

export default Header;
