import { ReactComponent as PeopleIcon } from '@/assets/icons/people.svg';
import { ReactComponent as TrashIcon } from '@/assets/icons/trash-can.svg';
import { ReactComponent as StampIcon } from '@/assets/icons/stamp.svg';
import { ReactComponent as KakaoIcon } from '@/assets/icons/kakaoauth.svg';
import { ReactComponent as ShareIcon } from '@/assets/icons/share-link.svg';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { useEffect, useState } from 'react';
import { MyTripTypes } from '@/types/mytrip/mytrip-types';
import { regionInfos } from '@/datas/regions';
import { Link } from 'react-router-dom';

import GridItem from './GridItem';
import Button from '@/components/atom/Button';
import Stamp from './Stamp';
import useToast from '@/hooks/useToast';
import getDday from '@/utils/date/getDday';
import useMyTripQuery from '@/queries/mytrip/useMyTripQuery';
import { getFormatDateString } from '@/utils/date';

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
  const [shareLink, setShareLink] = useState('');

  const toats = useToast();

  const onToggleEndTripHandler = () => {
    setEndTrip(!endTrip);
  };

  const getDdayString = (): string => {
    const dDay = getDday(startDate);
    let str = dDay > 0 ? `D+${dDay}` : `D-${Math.abs(dDay)}`;

    return str;
  };

  useEffect(() => {
    // 이후 배포주소로 변경 필요
    setShareLink(`http://localhost:5173/plan/detail/${scheduleId}`);
  }, []);

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
            <GridItem title="여행 이름" content={title} editable={true} />
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
            <Button hovercolor={'default'} className="p-0">
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
          <Button
            variant={'ring'}
            hovercolor={'default'}
            className="h-[50px] w-[120px] text-sm text-zinc-900"
          >
            일정 수정
          </Button>
          <div className="flex items-center gap-6">
            <Button
              variant={'ring'}
              hovercolor={'default'}
              className="h-[50px] w-[120px] text-sm text-zinc-900"
              onClick={() => setIsClickedShare(!isClickedShare)}
            >
              일정 공유
            </Button>
            {isClickedShare && (
              <>
                <CopyToClipboard
                  text={shareLink}
                  onCopy={() => toats({ content: '링크 복사 완료!', type: 'success' })}
                >
                  <Button
                    hovercolor={'default'}
                    variant={'ring'}
                    className="h-[50px] w-[50px] rounded-[100%] hover:scale-110 hover:transition-all"
                  >
                    <ShareIcon fill="#4568DC" />
                  </Button>
                </CopyToClipboard>

                <Button
                  hovercolor={'default'}
                  className="h-[50px] w-[50px] p-0 hover:scale-110 hover:transition-all"
                >
                  <KakaoIcon />
                </Button>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyTripCard;
