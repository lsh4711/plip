import { Button, MypageSideNav, Paragraph, SortingToolbar } from '@/components';
import { MyTripTypes } from '@/types/mytrip/mytrip-types';
import { useEffect, useState } from 'react';
import { ReactComponent as PlusIcon } from '@/assets/icons/plus-circle.svg';

import MyTripCard from '@/components/page-components/mytrip/MyTripCard';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import instance from '@/queries/axiosinstance';
import { Link, Navigate, useNavigate } from 'react-router-dom';

interface MyTripPageProps {}

const MyTripPage = ({}: MyTripPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const [dummyData, setDummyData] = useState<MyTripTypes[] | []>([]);

  useEffect(() => {
    fetch('/dummy/mytrip-dummy.json')
      .then((res) => res.json())
      .then((data) => setDummyData(data.data));
  }, []);

  return (
    <div className=" flex">
      <MypageSideNav />
      <div className="flex w-full flex-col px-8 pt-12">
        <SortingToolbar />
        <div
          className={` flex flex-1 flex-col ${
            dummyData.length > 0 ? '' : ' items-center justify-center'
          }`}
        >
          {dummyData.length > 0 ? (
            dummyData.map((item) => <MyTripCard key={item.scheduleId} {...item} />)
          ) : (
            <Link to="/plan" className=" flex flex-col items-center justify-center gap-2">
              <Paragraph>최근 여행 일정이 없습니다. 여행 계획해볼까요?</Paragraph>
              <Paragraph className=" flex items-center justify-center">
                <Button hovercolor={'default'} className="mr-2 p-0">
                  <PlusIcon width={18} height={18} />
                </Button>
                여행 일정 생성하러가기
              </Paragraph>
            </Link>
          )}
        </div>
      </div>
    </div>
  );
};

export default MyTripPage;
