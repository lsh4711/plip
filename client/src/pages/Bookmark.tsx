import { MypageSideNav } from '@/components';
import Preparing from '@/components/common/Preparing';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import React from 'react';

interface BookmarkProps {}

const Bookmark = ({}: BookmarkProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className=" flex">
      <MypageSideNav />
      <Preparing />
    </div>
  );
};

export default Bookmark;
