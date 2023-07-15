import { Button, MypageSideNav, SortingToolbar } from '@/components';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import defaultImage from '../../public/region/seoul.webp';

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
          <div className="mt-4 flex h-[220px] w-full rounded-lg border p-4 drop-shadow-lg">
            <div id="img" className="mr-4 shrink-0">
              <img src={defaultImage} alt="region" width={280} height={180} />
            </div>
            <div id="content" className="flex w-full flex-col border border-red-500">
              <div className="flex w-full flex-1 border border-yellow-500">
                <div className="flex-1 border border-blue-500"></div>
                <div className="w-[120px]"></div>
              </div>
              <div className="flex h-[80px] w-full items-center justify-evenly border border-green-500">
                <Button
                  variant={'ring'}
                  hovercolor={'default'}
                  className="h-[50px] w-[120px] text-sm text-zinc-900"
                >
                  여행일지
                </Button>
                <Button
                  variant={'ring'}
                  hovercolor={'default'}
                  className="h-[50px] w-[120px] text-sm text-zinc-900"
                >
                  일정상세보기
                </Button>
                <Button
                  variant={'ring'}
                  hovercolor={'default'}
                  className="h-[50px] w-[120px] text-sm text-zinc-900"
                >
                  일정 수정
                </Button>
                <Button
                  variant={'ring'}
                  hovercolor={'default'}
                  className="h-[50px] w-[120px] text-sm text-zinc-900"
                >
                  일정 공유
                </Button>
              </div>
            </div>
          </div>
          <div className="mt-4 h-[220px] w-full rounded-lg border drop-shadow-lg"></div>
          <div className="mt-4 h-[220px] w-full rounded-lg border drop-shadow-lg"></div>
        </div>
      </div>
    </div>
  );
};

export default MyTripPage;
