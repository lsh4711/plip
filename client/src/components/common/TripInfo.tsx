import { ReactComponent as ProfileIcon } from '@/assets/icons/profile.svg';
import HeadingParagraph from '@/components/atom/HeadingParagraph';
import Paragraph from '@/components/atom/Paragraph';
import { regionInfos } from '@/datas/regions';
import { ResponseData } from '@/pages/PlanMapPage';
import getFormatDateString from '@/utils/getFormatDateString';
import getTripPeriod from '@/utils/getTripPeriod';
import getTripTitleWithRegion from '@/utils/getTripTitleWithRegion';

function TripInfo({ title, region, startDate, endDate }: Omit<ResponseData, 'places'>) {
  return (
    <>
      <div
        style={{
          backgroundImage: `linear-gradient(to bottom, rgba(0,0,0,0.49), rgba(0,0,0,0.49)), url(${regionInfos[region].imgUrl})`,
          backgroundSize: 'cover',
          backgroundRepeat: 'no-repeat',
        }}
        className="flex h-52 flex-col gap-2 p-6 text-white"
      >
        <HeadingParagraph size={'lg'} variant={'default'}>
          {title ? title : getTripTitleWithRegion(region)}
        </HeadingParagraph>
        <Paragraph size={'default'} variant={'white'} weight={'bold'}>
          {`${getFormatDateString(startDate, true, 'dot')} ~ ${getFormatDateString(
            endDate,
            true,
            'dot'
          )}`}
        </Paragraph>
        <Paragraph size={'xs'} variant={'white'}>
          {`(${getTripPeriod(startDate, endDate)} 여행)`}
        </Paragraph>
        <ProfileIcon className="rounded-full bg-white" />
      </div>
    </>
  );
}

export default TripInfo;
