import { ReactComponent as ProfileIcon } from '@/assets/icons/profile.svg';
import HeadingParagraph from '@/components/atom/HeadingParagraph';
import Paragraph from '@/components/atom/Paragraph';
import { regionInfos } from '@/datas/regions';
import { ResponseData } from '@/pages/PlanMapPage/PlanMapPage';
import { getFormatDateString, getTripPeriod, getTripTitleWithRegion } from '@/utils/date';

function TripInfo({ title, region, startDate, endDate }: Omit<ResponseData, 'places'>) {
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
        <HeadingParagraph size={'md'} variant={'default'} className="2xl:text-3xl">
          {title ? title : getTripTitleWithRegion(region)}
        </HeadingParagraph>
        <Paragraph size={'xs'} variant={'white'} weight={'bold'} className="text-xs 2xl:text-sm">
          {`${getFormatDateString(startDate, true, 'dot')} ~ ${getFormatDateString(
            endDate,
            true,
            'dot'
          )} (${getTripPeriod(startDate, endDate)} 여행)`}
        </Paragraph>
        <ProfileIcon className="h-6 w-6 rounded-full bg-white 2xl:h-8 2xl:w-8" />
      </div>
    </>
  );
}

export default TripInfo;
