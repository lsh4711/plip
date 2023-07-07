import React from 'react';
import Button from '../atom/Button';
import { Link, useLocation } from 'react-router-dom';

interface MypageSideNavProps {}

const MypageSideNav = ({}: MypageSideNavProps) => {
  const path = useLocation();
  console.log(path);
  return (
    <nav className=" sticky left-0 right-0 top-0 h-screen w-[18.75rem] border-r border-zinc-400 pt-[6rem] ">
      <div className=" flex flex-col items-end gap-y-[1.5rem] pr-6">
        {buttonArray.map((item, idx) => (
          <Button
            className=" text-[1.25rem] font-normal hover:text-zinc-200"
            variant={path.pathname === item.slug ? 'primary' : 'default'}
          >
            <Link to={item.slug}>{item.subject}</Link>
          </Button>
        ))}
      </div>
    </nav>
  );
};

export default MypageSideNav;

const buttonArray = [
  {
    subject: '나의 일정',
    slug: '/mypage/mytrip',
  },
  {
    subject: '마이페이지',
    slug: '/mypage',
  },
  {
    subject: '나의 발자취',
    slug: '/mypage/footprint',
  },
  {
    subject: '북마크',
    slug: '/mypage/bookmark',
  },
  {
    subject: '나의 일지',
    slug: '/mypage/myrecord',
  },
];
