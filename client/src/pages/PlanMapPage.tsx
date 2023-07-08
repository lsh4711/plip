import SidePanel from '@/components/common/SidePanel';
import TripInfo from '@/components/common/TripInfo';
import TripSchedule from '@/components/common/TripSchedule';
import { regions } from '@/datas/regions';

export type ResponseData = {
  title: string | null;
  region: (typeof regions)[number];
  startDate: Date;
  endDate: Date;
  places: any;
};

const PlanMapPage = () => {
  const responseData: ResponseData = {
    title: null,
    region: 'seoul',
    startDate: new Date(),
    endDate: new Date(),
    places: [
      [{ placeName: '인천 국제 공항' }, { placeName: '버스터미널' }],
      [{ placeName: '평창역' }, { placeName: '사근진 해변' }],
    ],
  };

  return (
    <div>
      <SidePanel position={'right'}>
        <TripInfo
          title={responseData.title}
          region={responseData.region}
          startDate={responseData.startDate}
          endDate={responseData.endDate}
        />
        <TripSchedule startDate={responseData.startDate} places={responseData.places} />
      </SidePanel>
    </div>
  );
};

export default PlanMapPage;
