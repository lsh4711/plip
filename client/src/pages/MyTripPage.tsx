import { MypageSideNav } from '@/components';
import { Link } from 'react-router-dom';

interface MyTripPageProps {}

const MyTripPage = ({}: MyTripPageProps) => {
  return (
    <div className=" flex">
      <MypageSideNav />
      <Link to={'/signout'}>회원탈퇴 실험</Link>
    </div>
  );
};

export default MyTripPage;
