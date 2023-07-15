import { useRef } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { ReactComponent as ArrowDownIcon } from '../../assets/icons/arrow-down.svg';
import { ReactComponent as MypageIcon } from '../../assets/icons/mypage.svg';
import { ReactComponent as NotifyIcon } from '../../assets/icons/notification.svg';
import { ReactComponent as LogoIcon } from '../../assets/logo.svg';

import Button from '../atom/Button';
import DropDownMenus from './DropDownMenus';
import { useCloseDropdown } from '@/hooks/useCloseDropdown';
import Avatar from './Avatar';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import useInquireUsersQuery from '@/queries/useInquireUsersQuery';
import instance from '@/queries/axiosinstance';

interface HeaderProps {
  isHome?: boolean;
}

interface AfterHeaderProps extends HeaderProps {
  username?: string;
}

const BeforeLogin = ({ isHome }: HeaderProps) => {
  // admin 계정 로그인 함수 / 앞단 유효성 때문에 어드민 로그인 안됨

  const onMasterLogin = () => {
    const username = import.meta.env.VITE_ADMIN_ID;
    const password = import.meta.env.VITE_ADMIN_PW;

    instance
      .post(
        '/api/users/login',
        {
          username,
          password,
        },
        { withCredentials: true }
      )
      .then((res) => {
        console.log(res);
      });
  };

  return (
    <>
      <Button className={isHome ? 'text-white' : ''} onClick={onMasterLogin}>
        <Link to="#">Master</Link>
      </Button>
      <Button className={isHome ? 'text-white' : ''}>
        <Link to="/login">Sign in</Link>
      </Button>
      <Button variant={'primary'}>
        <Link to="/signup">Sign up</Link>
      </Button>
    </>
  );
};

const AfterLogin = ({ isHome }: AfterHeaderProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const [isOpen, setIsOpen] = useCloseDropdown(ref, false);
  const inquireQuery = useInquireUsersQuery();
  return (
    <>
      <Link to="/mypage/mytrip">
        <MypageIcon />
      </Link>
      <Link to="#">
        <NotifyIcon />
      </Link>
      <Link to="/mypage">
        <Avatar />
      </Link>
      <div
        className={`flex cursor-pointer select-none items-center text-sm ${isHome && 'text-white'}`}
        ref={ref}
        onClick={() => setIsOpen(!isOpen)}
      >
        {inquireQuery.data?.data.data.nickname} 님
        <ArrowDownIcon width={18} height={18} transform={isOpen ? 'rotate(180)' : ''} />
        {isOpen && <DropDownMenus />}
      </div>
    </>
  );
};

const Header = () => {
  const isHome = useLocation().pathname === '/';
  const isLogin = useSelector((state: RootState) => state.auth.isLogin);

  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);
  const inquireQuery = useInquireUsersQuery();
  return (
    <header
      className={`left-0 top-0 z-40 h-[80px] w-full px-12 ${
        isHome ? ' fixed' : 'stikcy border border-b-2 bg-white'
      }`}
    >
      <nav className="m-auto flex h-full items-center justify-between">
        <Link to="/">
          <div className="flex items-center gap-2">
            <LogoIcon width={35} height={35} />
            <span className="gradient-text text-xl font-bold">PliP</span>
          </div>
        </Link>
        <div className="relative flex items-center gap-4">
          {isLogin ? <AfterLogin isHome={isHome} /> : <BeforeLogin isHome={isHome} />}
        </div>
      </nav>
    </header>
  );
};

export default Header;
