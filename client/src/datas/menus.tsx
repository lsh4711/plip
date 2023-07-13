import { ReactComponent as MyTripIcon } from '@/assets/icons/mytrip.svg';
import { ReactComponent as MyPageIcon } from '@/assets/icons/octoface.svg';
import { ReactComponent as MyTraceIcon } from '@/assets/icons/mytrace.svg';
import { ReactComponent as BookmarkIcon } from '@/assets/icons/bookmark.svg';
import { ReactComponent as MyRecordIcon } from '@/assets/icons/myrecord.svg';
import { ReactComponent as LogoutIcon } from '@/assets/icons/logout.svg';

export type MenuProps = {
  icon: SVGAElement;
  name: string;
  route: string;
};

// '나의 일정', '마이페이지', '나의 발자취', '북마크', '나의 일지', '로그아웃';

export const menus = [
  {
    icon: <MyTripIcon />,
    name: '나의 일정',
    route: '/mypage/mytrip',
  },
  {
    icon: <MyPageIcon />,
    name: '마이페이지',
    route: '/mypage',
  },
  {
    icon: <MyTraceIcon />,
    name: '나의 발자취',
    route: 'mypage/footprint',
  },
  {
    icon: <BookmarkIcon />,
    name: '북마크',
    route: '/bookmark',
  },
  {
    icon: <MyRecordIcon />,
    name: '나의 일지',
    route: '/mypage/myrecord',
  },
];
