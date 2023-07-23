import { useEffect } from 'react';

import { HeadingParagraph, MyrecordImg, SortingToolbar } from '@/components';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import instance from '@/queries/axiosinstance';
import { Record } from '@/types/api/records-types';

interface MyRecordPageProps {}

const MyRecordPage = ({}: MyRecordPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  useEffect(() => {
    instance.get('/api/records', { withCredentials: true }).then((data) => console.log(data));
  }, []);

  return (
    <div className=" flex flex-col">
      <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-4">
        나의 일지
      </HeadingParagraph>
      <SortingToolbar />
      <div className=" mt-6 grid cursor-pointer grid-cols-1 gap-4 md:grid-cols-2 lg:grid-cols-3  xl:grid-cols-5 xl:gap-6">
        {mockdata.map((item, idx) => (
          <MyrecordImg key={`img${idx}`} record={item} />
        ))}
      </div>
    </div>
  );
};

export default MyRecordPage;

const mockdata: Record[] = [
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
  {
    content: '제주도',
    placeName: '협재해수욕장',
    createdAt: '2023-06-23',
    memberId: '123',
    modifiedAt: '2023-06-24',
    recordId: '456',
  },
];
