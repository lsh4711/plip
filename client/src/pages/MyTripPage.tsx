import { MypageSideNav } from '@/components';
import SideBar from '@/components/common/SideBar';

interface MyTripPageProps {}

const MyTripPage = ({}: MyTripPageProps) => {
  return (
    <div className=" flex">
      <MypageSideNav />
      <div>MyTripPage</div>
    </div>
  );
};

export default MyTripPage;
