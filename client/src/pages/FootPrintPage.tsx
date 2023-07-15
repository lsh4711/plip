import { MypageSideNav } from '@/components';
import Preparing from '@/components/common/Preparing';
import useAuthRedirect from '@/hooks/useAuthRedirect';

interface FootPrintPageProps {}

const FootPrintPage = ({}: FootPrintPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className=" flex">
      <MypageSideNav />
      <Preparing />
    </div>
  );
};

export default FootPrintPage;
