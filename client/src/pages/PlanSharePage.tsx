import SidePanel from '@/components/common/SidePanel';
import { Map, MenuButtons, ZoomButtons } from '@/components/map';
import { TripInfo, TripSchedule } from '@/components/schedule';
import { usePlanQuery } from '@/queries/plan';
import useSharePlanQuery from '@/queries/plan/useSharePlanQuery';
import { RootState } from '@/redux/store';
import { getRegionCenterLat, getRegionCenterLng } from '@/utils/map';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams, useSearchParams } from 'react-router-dom';

const BASE_URL = import.meta.env.VITE_BASE_URL;

const PlanSharePage = () => {
  const { id } = useParams();
  const [searchParams] = useSearchParams();
  const userId = searchParams.get('id');
  const userEmail = searchParams.get('email');
  const { data, isLoading, error } = useSharePlanQuery({
    planId: id!,
    userId: userId!,
    userEmail: userEmail!,
  });

  console.log(data);

  const { schedules } = useSelector((state: RootState) => state.schedule);

  const [mapLevel, setMapLevel] = useState(8);

  useEffect(() => {
    axios
      .get(`${BASE_URL}/api/schedules/${id}/share?id=3&email=test@naver.com`)
      .then((res) => console.log(res));
  }, []);

  return (
    <div className="relative h-full w-full">
      <>
        <Map
          type="recording"
          centerLat={getRegionCenterLat(data?.region!)}
          centerLng={getRegionCenterLng(data?.region!)}
          mapLevel={mapLevel}
          setMapLevel={setMapLevel}
          schedules={schedules}
          showPolyline
        />

        <SidePanel position={'right'}>
          <TripInfo
            title={data?.title!}
            region={data?.region!}
            startDate={data?.startDate!}
            endDate={data?.endDate!}
          />
          <TripSchedule startDate={data?.startDate!} places={schedules} />

          <ZoomButtons
            onClickZoomIn={() => {
              setMapLevel(mapLevel > 1 ? mapLevel - 1 : 1);
            }}
            onClickZoomOut={() => {
              setMapLevel(mapLevel < 14 ? mapLevel + 1 : 14);
            }}
            className={'absolute -left-16 bottom-6 z-50'}
          />
        </SidePanel>
      </>
    </div>
  );
};

export default PlanSharePage;
