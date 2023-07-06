import { Button, Image, MyrecordImg, Paragraph, SortingToolbar } from '@/components';
import MypageSideNav from '@/components/helper/MypageSideNav';
import { Record } from '@/types/api/records-types';
import { Link } from 'react-router-dom';

interface MyRecordPageProps {}

const MyRecordPage = ({}: MyRecordPageProps) => {
  return (
    <main className=" flex">
      <MypageSideNav />
      <div className=" flex flex-col pl-16 pt-12">
        <SortingToolbar />
        <div className=" mt-6 grid cursor-pointer grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3  xl:grid-cols-5 xl:gap-6">
          {mockdata.map((item, idx) => (
            <MyrecordImg key={`img${idx}`} record={item} />
          ))}
        </div>
      </div>
    </main>
  );
};

export default MyRecordPage;

const mockdata: Record[] = [
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    title: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
];
