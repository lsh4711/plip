import { MypageSideNav } from '@/components';
import SideBar from '@/components/common/SideBar';

interface MyTripPageProps {}

const MyTripPage = ({}: MyTripPageProps) => {
  return (
    <div className=" flex">
      <MypageSideNav />
      <button onClick={() => {}}>로그아웃버튼</button>
    </div>
  );
};

export default MyTripPage;
