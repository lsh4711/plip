import { MypageSideNav } from '@/components';
import useAuthRedirect from '@/hooks/useAuthRedirect';

interface FootPrintPageProps {}

const FootPrintPage = ({}: FootPrintPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className=" flex">
      <MypageSideNav />
    </div>
  );
};

export default FootPrintPage;
