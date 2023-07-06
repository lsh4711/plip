import { Link } from 'react-router-dom';
import { ReactComponent as ArrowDownIcon } from '../../assets/icons/arrow-down.svg';
import { ReactComponent as MypageIcon } from '../../assets/icons/mypage.svg';
import { ReactComponent as NotifyIcon } from '../../assets/icons/notification.svg';
import { ReactComponent as ProfileIcon } from '../../assets/icons/profile.svg';
import { ReactComponent as LogoIcon } from '../../assets/logo.svg';

import Button from '../atom/Button';

interface HeaderProps {
  isHome?: boolean;
}

const BeforeLogin = ({ isHome }: { isHome?: boolean }) => {
  console.log(isHome);
  return (
    <>
      <Button className={isHome ? 'text-white' : ''}>
        <Link to="/login">Sign in</Link>
      </Button>
      <Button variant={'primary'}>
        <Link to="/signup">Sign up</Link>
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
      <div className="flex items-center text-sm">
        {username} 님
        <ArrowDownIcon width={12} height={12} />
      </div>
    </>
  );
};

const Header = ({ isHome }: HeaderProps) => {
  const isLogin = false; // 로그인 상태 구현전 임시 변수입니다.

  return (
    <header
      className={`fixed left-0 top-0 z-50 h-[80px] w-full px-12 ${
        !isHome && 'border border-b-2 bg-white'
      }`}
    >
      <nav className="m-auto flex h-full items-center justify-between">
        <Link to="/">
          <div className="flex items-center gap-2">
            <LogoIcon width={35} height={35} />
            <span className="gradient-text text-xl font-bold">PliP</span>
          </div>
        </Link>
        <div className="flex items-center gap-4">
          {isLogin ? <AfterLogin /> : <BeforeLogin isHome={isHome} />}
        </div>
      </nav>
    </header>
  );
};

export default Header;
