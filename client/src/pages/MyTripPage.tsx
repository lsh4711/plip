import { MypageSideNav, SortingToolbar } from '@/components';
import MyTripCard from '@/components/page-components/mytrip/MyTripCard';

import useAuthRedirect from '@/hooks/useAuthRedirect';
import { MyTripTypes } from '@/types/mytrip/mytrip-types';
import { useEffect, useState } from 'react';

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
        <div className="mt-6 flex flex-col">
          {dummyData.map((item) => (
            <MyTripCard key={item.scheduleId} {...item} />
          ))}

          {/* <MyTripCard isEnd={true} />
          <MyTripCard /> */}
        </div>
      </div>
    </div>
  );
};

export default MyTripPage;
