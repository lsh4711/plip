import { Button, Image, MyrecordImg, Paragraph, SortingToolbar } from '@/components';
import MypageSideNav from '@/components/helper/MypageSideNav';
import { Link } from 'react-router-dom';

interface MyRecordPageProps {}

const MyRecordPage = ({}: MyRecordPageProps) => {
  return (
    <main className=" flex">
      <MypageSideNav />
      <div className=" flex flex-col pl-16 pt-12">
        <SortingToolbar />
        <div className=" mt-6 grid cursor-pointer grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4"></div>
        <MyrecordImg />
      </div>
    </main>
  );
};

export default MyRecordPage;
