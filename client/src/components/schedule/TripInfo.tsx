import { ReactComponent as ProfileIcon } from '@/assets/icons/profile.svg';
import HeadingParagraph from '@/components/atom/HeadingParagraph';
import Paragraph from '@/components/atom/Paragraph';
import { regionInfos } from '@/datas/regions';
import { Schedule } from '@/types/api/schedules-types';
import { getFormatDateString, getTripPeriod, getTripTitleWithRegion } from '@/utils/date';

function TripInfo({
  title,
  region,
  startDate,
  endDate,
}: Pick<Schedule, 'title' | 'region' | 'startDate' | 'endDate'>) {
  return (
    <>
      <div
        style={{
          backgroundImage: `linear-gradient(to bottom, rgba(0,0,0,0.49), rgba(0,0,0,0.49)), url(${regionInfos[region].imgUrl})`,
          backgroundSize: 'cover',
          backgroundRepeat: 'no-repeat',
        }}
        className="flex flex-col gap-3 px-6 py-9 text-white"
      >
        <HeadingParagraph
          size={'md'}
          variant={'default'}
          className="block overflow-hidden text-ellipsis whitespace-nowrap 2xl:text-2xl"
        >
          {title ? title : getTripTitleWithRegion(region)}
        </HeadingParagraph>
        <Paragraph
          size={'xs'}
          variant={'white'}
          weight={'bold'}
          className="whitespace-pre-line text-sm"
        >
          {`${getFormatDateString(startDate, true, 'korean')} ~
           ${getFormatDateString(endDate, true, 'korean')} (${getTripPeriod(
            startDate,
            endDate
          )} 여행)`}
        </Paragraph>
        <ProfileIcon className="h-6 w-6 rounded-full bg-white 2xl:h-8 2xl:w-8" />
      </div>
    </>
  );
}

export default TripInfo;
