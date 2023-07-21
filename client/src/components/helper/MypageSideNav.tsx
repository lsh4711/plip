import { FcDisapprove } from '@react-icons/all-files/fc/FcDisapprove';
import { NavLink, useLocation } from 'react-router-dom';

import { menus } from '@/datas/menus';

interface MypageSideNavProps {}

const MypageSideNav = ({}: MypageSideNavProps) => {
  const path = useLocation();
  console.log(path);
  return (
    <nav className="fixed left-0 top-[76px] hidden h-screen w-56 shrink-0 flex-col gap-2 border-r-2 px-2 pt-8 md:flex">
      <span className="px-4 text-sm text-zinc-400">마이페이지</span>
      {menus.map((menu, idx) => {
        if (idx === 0) return;
        return (
          <NavLink to={menu.route}>
            {({ isActive }) => (
              <div
                className="flex w-full gap-2 rounded-lg px-4 py-2"
                style={
                  isActive
                    ? { backgroundColor: '#4568DC20', color: '#4568DC', fontWeight: 600 }
                    : {}
                }
              >
                {menu.icon}
                {menu.name}
              </div>
            )}
          </NavLink>
        );
      })}
      <NavLink to="/mypage/signout">
        {({ isActive }) => (
          <div
            className="flex gap-2 rounded-lg px-4 py-2"
            style={
              isActive ? { backgroundColor: '#4568DC20', color: '#4568DC', fontWeight: 600 } : {}
            }
          >
            <FcDisapprove size={20} />
            회원 탈퇴
          </div>
        )}
      </NavLink>
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
