import { MypageSideNav, SortingToolbar } from '@/components';
import MyTripCard from '@/components/page-components/mytrip/MyTripCard';

import useAuthRedirect from '@/hooks/useAuthRedirect';

interface MyTripPageProps {}

const MyTripPage = ({}: MyTripPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className=" flex">
      <MypageSideNav />
      <div className="flex w-full flex-col px-8 pt-12">
        <SortingToolbar />
        <div className="mt-6 flex flex-col">
          <MyTripCard />
          <MyTripCard isEnd={true} />
          <MyTripCard />
        </div>
      </div>
    </div>
  );
};

export default MyTripPage;
