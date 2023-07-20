import React, { useRef } from 'react';
import { useSelector } from 'react-redux';
import { Link, useLocation } from 'react-router-dom';

import { useCloseDropdown } from '@/hooks/useCloseDropdown';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import instance from '@/queries/axiosinstance';
import { RootState } from '@/redux/store';
import { ReactComponent as ArrowDownIcon } from '../../assets/icons/arrow-down.svg';
import { ReactComponent as MypageIcon } from '../../assets/icons/mypage.svg';
import { ReactComponent as NotifyIcon } from '../../assets/icons/notification.svg';
import { ReactComponent as LogoIcon } from '../../assets/logo.svg';
import Button from '../atom/Button';
import LoadingSpinner from '../atom/LoadingSpinner';
import Avatar from './Avatar';
import DropDownMenus from './DropDownMenus';

interface HeaderProps {
  isHome?: boolean;
}

interface AfterHeaderProps extends HeaderProps {
  username?: string;
}

const BeforeLogin = ({ isHome }: HeaderProps) => {
  return (
    <>
      <Link to="/login">
        <Button className={isHome ? 'text-white' : ''}>Sign in</Button>
      </Link>
      <Link to="/signup">
        <Button variant={'primary'}>Sign up</Button>
      </Link>
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

  return (
    <header
      className={`left-0 top-0 z-40 w-full px-12 py-5 ${
        isHome ? ' fixed' : 'stikcy border-b-2 bg-white'
      }`}
    >
      <nav className="m-auto flex h-full items-center justify-between">
        <Link to="/">
          <div className="flex items-center gap-2">
            <LogoIcon width={35} height={35} />
            <span className="gradient-text text-xl font-bold">PliP</span>
          </div>
        </Link>
        <React.Suspense fallback={<LoadingSpinner />}>
          <div className="relative flex items-center gap-4">
            {isLogin ? <AfterLogin isHome={isHome} /> : <BeforeLogin isHome={isHome} />}
          </div>
        </React.Suspense>
      </nav>
    </header>
  );
};

export default Header;
