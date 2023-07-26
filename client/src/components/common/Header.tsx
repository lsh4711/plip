import { CgMenu } from '@react-icons/all-files/cg/CgMenu';
import React, { useRef } from 'react';
import { useSelector } from 'react-redux';
import { Link, useLocation } from 'react-router-dom';

import { useCloseDropdown } from '@/hooks/useCloseDropdown';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import { RootState } from '@/redux/store';
import { ReactComponent as ArrowDownIcon } from '../../assets/icons/arrow-down.svg';
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
      {/* <Button className={isHome ? 'text-white' : ''} onClick={onMasterLogin}>
        <Link to="#">Master</Link>
      </Button> */}
      <Button
        hovercolor={'default'}
        className={`hover:text-[#4568DC] ${isHome ? 'text-white' : 'hover:bg-[#4568DC]/10'}`}
      >
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
      <Link to="/mypage/info">
        <Avatar />
      </Link>
      <div
        className={`hidden cursor-pointer select-none items-center text-sm md:flex ${
          isHome && 'text-white'
        }`}
        ref={ref}
        onClick={() => setIsOpen(!isOpen)}
      >
        {inquireQuery.data?.nickname} 님
        <ArrowDownIcon width={18} height={18} transform={isOpen ? 'rotate(180)' : ''} />
        {isOpen && <DropDownMenus />}
      </div>
    </>
  );
};

const Header = () => {
  const isHome = useLocation().pathname === '/';
  const isLogin = useSelector((state: RootState) => state.auth.isLogin);
  const ref = useRef<HTMLDivElement>(null);
  const [isOpen, setIsOpen] = useCloseDropdown(ref, false);

  return (
    <header
      className={`left-0 top-0 z-40 h-[76px] w-full px-8 ${
        isHome ? ' fixed' : 'fixed border-b-2 bg-white'
      }`}
    >
      <nav className="m-auto flex h-full items-center justify-between">
        {!isHome && isLogin && (
          <span ref={ref} className="cursor-pointer md:hidden" onClick={() => setIsOpen(!isOpen)}>
            <CgMenu size={25} color="#343539" />
            {isOpen && <DropDownMenus variant={'mobile'} />}
          </span>
        )}
        <Link to="/">
          <div className="flex items-center gap-2">
            <LogoIcon aria-label="back to home page" width={35} height={35} />
            <span className="gradient-text hidden text-xl font-bold md:block">PliP</span>
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
