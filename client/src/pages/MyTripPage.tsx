import { Link } from 'react-router-dom';

import { ReactComponent as PlusIcon } from '@/assets/icons/plus-circle.svg';
import { Button, HeadingParagraph, Paragraph, SortingToolbar } from '@/components';
import LoadingSpinner from '@/components/atom/LoadingSpinner';
import MyTripCard from '@/components/page-components/mytrip/MyTripCard';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import useMyTripQuery from '@/queries/mytrip/useMyTripQuery';

const MyTripPage = () => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const { data, isLoading, error } = useMyTripQuery();

  return (
    <div className="flex w-full flex-col">
      <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-4">
        나의 일정
      </HeadingParagraph>
      <SortingToolbar />
      <div
        className={` flex flex-1 flex-col ${
          data!.length > 0 ? '' : ' items-center justify-center'
        }`}
      >
        {isLoading && <LoadingSpinner />}
        {!isLoading && data!.length > 0 ? (
          data!.map((item) => <MyTripCard key={item.scheduleId} {...item} />)
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
  );
};

export default MyTripPage;
