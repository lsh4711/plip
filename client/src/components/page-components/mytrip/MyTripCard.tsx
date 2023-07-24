import { ReactComponent as PeopleIcon } from '@/assets/icons/people.svg';
import { ReactComponent as TrashIcon } from '@/assets/icons/trash-can.svg';
import { ReactComponent as StampIcon } from '@/assets/icons/stamp.svg';

import { useState } from 'react';
import { MyTripTypes } from '@/types/mytrip/mytrip-types';
import { regionInfos } from '@/datas/regions';
import { Link } from 'react-router-dom';
import { getFormatDateString } from '@/utils/date';

import GridItem from './GridItem';
import Button from '@/components/atom/Button';
import Stamp from './Stamp';
import getDday from '@/utils/date/getDday';
import useModal from '@/hooks/useModal';
import Confirm from '@/components/common/Confirm';
import useRemoveTripMutation from '@/queries/mytrip/useRemoveTripMutation';
import LoadingSpinner from '@/components/atom/LoadingSpinner';
import SharesButtons from '@/components/common/SharesButtons';
import { UserGetRequest } from '@/types/api/users-types';

const MyTripCard = ({
  scheduleId,
  title,
  region,
  memberCount,
  placeSize,
  isEnd,
  modifiedAt,
  startDate,
  endDate,
}: MyTripTypes) => {
  const [endTrip, setEndTrip] = useState(isEnd);
  const [isClickedShare, setIsClickedShare] = useState(false);

  const [openModal] = useModal();
  const removeTripMutation = useRemoveTripMutation();

  const onClickShareButtons = async () => {
    setIsClickedShare(!isClickedShare);
  };

  const onToggleEndTripHandler = () => {
    setEndTrip(!endTrip);
  };

  const onDeleteTrip = () => {
    const onConfirm = (close: () => void) => {
      removeTripMutation.mutateAsync(scheduleId).then(() => {
        close();
      });
    };

    openModal(({ isOpen, close }) => (
      <Confirm
        type={'warning'}
        title={'일정 삭제'}
        content={'일정을 삭제하시겠습니까? 삭제된 일정은 복구할 수 없습니다.'}
        primaryLabel={`${removeTripMutation.status === 'loading' ? <LoadingSpinner /> : '확인'}`}
        secondaryLabel={'취소'}
        isOpen={isOpen}
        onClose={close}
        onClickPrimaryButton={() => onConfirm(close)}
      />
    ));
  };

  const getDdayString = (): string => {
    const dDay = getDday(startDate);
    let str = dDay > 0 ? `D+${dDay}` : `D-${Math.abs(dDay)}`;

    return str;
  };

  return (
    <div className="relatvie mt-4 flex h-[220px] w-full gap-16 rounded-lg border p-4 drop-shadow-lg">
      <span className="absolute left-0 top-0 z-30 h-[40px] w-[40px] items-center rounded-[100%] border bg-[#4568DC] text-center text-[8px] leading-[36px]  text-white">
        {getDdayString()}
      </span>
      <div id="img" className="relative mr-4 shrink-0 ">
        <img
          src={regionInfos[region].imgUrl}
          alt="region"
          width={280}
          height={180}
          className={endTrip ? 'opacity-50' : ''}
        />
        {endTrip && <Stamp />}
      </div>
      <div id="content" className="flex w-full flex-col">
        {/* 상단 */}
        <div className="flex w-full flex-1 pt-4">
          <div className="grid flex-1 grid-cols-2 items-center gap-2">
            <GridItem id={scheduleId} title="여행 이름" content={title} editable={true} />
            <GridItem
              title="마지막 수정일"
              content={getFormatDateString(modifiedAt, false, 'dash')}
            />
            <GridItem
              title="우리 여행가요!"
              content={`${getFormatDateString(startDate, false, 'dash')}~${getFormatDateString(
                endDate,
                false,
                'dash'
              )}`}
            />
            <GridItem title="여행 장소" content={placeSize} />
            <GridItem title={<PeopleIcon width={16} height={16} />} content={memberCount} />
          </div>

          <div className="flex w-[120px] items-center justify-center gap-4">
            <Button hovercolor={'default'} className="p-0" onClick={onToggleEndTripHandler}>
              <StampIcon fill={endTrip ? '#4568DC' : ''} />
            </Button>
            <Button hovercolor={'default'} className="p-0" onClick={onDeleteTrip}>
              <TrashIcon />
            </Button>
          </div>
        </div>
        {/* 하단 */}
        <div className="flex h-[80px] w-full items-center gap-16">
          <Button
            variant={'ring'}
            hovercolor={'default'}
            className="h-[50px] w-[120px] text-sm text-zinc-900"
            onClick={() => alert('🚧준비중입니당!!')}
          >
            여행일지
          </Button>
          <Link to={`/plan/detail/${scheduleId}`}>
            <Button
              variant={'ring'}
              hovercolor={'default'}
              className="h-[50px] w-[120px] text-sm text-zinc-900"
            >
              일정상세보기
            </Button>
          </Link>
          <Link to={`/plan/map/${scheduleId}`}>
            <Button
              variant={'ring'}
              hovercolor={'default'}
              className="h-[50px] w-[120px] text-sm text-zinc-900"
            >
              일정 수정
            </Button>
          </Link>
          <div className="flex items-center gap-6">
            <Button
              variant={'ring'}
              hovercolor={'default'}
              className="h-[50px] w-[120px] text-sm text-zinc-900"
              onClick={onClickShareButtons}
            >
              일정 공유
            </Button>
            {isClickedShare && <SharesButtons scheduleId={scheduleId} />}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyTripCard;
