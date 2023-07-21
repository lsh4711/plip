import { FcBookmark } from '@react-icons/all-files/fc/FcBookmark';
import { FcGlobe } from '@react-icons/all-files/fc/FcGlobe';
import { FcCalendar } from '@react-icons/all-files/fc/FcCalendar';
import { FcStackOfPhotos } from '@react-icons/all-files/fc/FcStackOfPhotos';
import { FcApprove } from '@react-icons/all-files/fc/FcApprove';

export type MenuProps = {
  icon: SVGAElement;
  name: string;
  route: string;
};

// '나의 일정', '마이페이지', '나의 발자취', '북마크', '나의 일지', '로그아웃';

export const menus = [
  {
    icon: <FcCalendar size={20} />,
    name: '나의 일정',
    route: '/mypage/mytrip',
  },
  {
    icon: <FcApprove size={20} />,
    name: '마이페이지',
    route: '/mypage',
  },
  {
    icon: <FcGlobe size={20} />,
    name: '나의 발자취',
    route: '/mypage/footprint',
  },
  {
    icon: <FcBookmark size={20} />,
    name: '북마크',
    route: '/mypage/bookmark',
  },
  {
    icon: <FcStackOfPhotos size={20} />,
    name: '나의 일지',
    route: '/mypage/myrecord',
  },
];
