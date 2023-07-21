import Preparing from '@/components/common/Preparing';
import useAuthRedirect from '@/hooks/useAuthRedirect';

interface BookmarkProps {}

const Bookmark = ({}: BookmarkProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className="flex grow">
      <Preparing />
    </div>
  );
};

export default Bookmark;
