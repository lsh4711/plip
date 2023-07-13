import { useEffect, useRef } from 'react';
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
import { EMPTY_TOKEN } from '@/redux/slices/authSlice';

interface HeaderProps {
  isHome?: boolean;
}

interface AfterHeaderProps extends HeaderProps {
  username?: string;
}

const BeforeLogin = ({ isHome }: HeaderProps) => {
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

const AfterLogin = ({ isHome, username }: AfterHeaderProps) => {
  const ref = useRef<HTMLDivElement>(null);
  const [isOpen, setIsOpen] = useCloseDropdown(ref, false);
  const accesstoken = useSelector((state: RootState) => state.auth.accesstoken);
  const inquireQuery = useInquireUsersQuery();
  useEffect(() => {
    if (accesstoken !== EMPTY_TOKEN) {
      inquireQuery.refetch();
    }
  }, [accesstoken]);

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
        {username} 님
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
  useEffect(() => {
    if (accesstoken !== EMPTY_TOKEN) {
      inquireQuery.refetch();
    }
  }, [accesstoken, inquireQuery.data?.data.data.nickname]);
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
          {isLogin ? (
            <AfterLogin isHome={isHome} username={inquireQuery.data?.data.data.nickname} />
          ) : (
            <BeforeLogin isHome={isHome} />
          )}
        </div>
      </nav>
    </header>
  );
};

export default Header;
