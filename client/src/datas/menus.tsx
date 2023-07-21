import { FcApprove } from '@react-icons/all-files/fc/FcApprove';
import { FcBookmark } from '@react-icons/all-files/fc/FcBookmark';
import { FcBriefcase } from '@react-icons/all-files/fc/FcBriefcase';
import { FcCalendar } from '@react-icons/all-files/fc/FcCalendar';
import { FcGlobe } from '@react-icons/all-files/fc/FcGlobe';
import { FcStackOfPhotos } from '@react-icons/all-files/fc/FcStackOfPhotos';

export type MenuProps = {
  icon: SVGAElement;
  name: string;
  route: string;
};

// '나의 일정', '마이페이지', '나의 발자취', '북마크', '나의 일지', '로그아웃';

export const menus = [
  {
    icon: <FcBriefcase size={20} />,
    name: '계획 작성',
    route: '/plan',
  },
  {
    icon: <FcCalendar size={20} />,
    name: '나의 일정',
    route: '/mypage/mytrip',
  },
  {
    icon: <FcGlobe size={20} />,
    name: '나의 발자취',
    route: '/mypage/footprint',
  },
  {
    icon: <FcStackOfPhotos size={20} />,
    name: '나의 일지',
    route: '/mypage/myrecord',
  },
  {
    icon: <FcBookmark size={20} />,
    name: '북마크',
    route: '/mypage/bookmark',
  },
  {
    icon: <FcApprove size={20} />,
    name: '회원 정보',
    route: '/mypage/info',
  },
];
