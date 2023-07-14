import { MypageSideNav } from '@/components';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import React from 'react';

interface BookmarkProps {}

const Bookmark = ({}: BookmarkProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className=" flex">
      <MypageSideNav />
      <div className="flex"></div>
    </div>
  );
};

export default Bookmark;
