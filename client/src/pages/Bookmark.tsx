import useAuthRedirect from '@/hooks/useAuthRedirect';
import React from 'react';

interface BookmarkProps {}

const Bookmark = ({}: BookmarkProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return <div>Bookmark</div>;
};

export default Bookmark;
