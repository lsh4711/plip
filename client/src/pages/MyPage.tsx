import Header from '@/components/common/Header';
import { Outlet } from 'react-router-dom';
interface MyPageProps {}

const MyPage = ({}: MyPageProps) => {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
};

export default MyPage;
