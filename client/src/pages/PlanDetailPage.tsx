import { Button } from '@/components';
import { Map, MenuButtons, SearchTools, ZoomButtons } from '@/components/map';
import { usePlanQuery } from '@/queries/plan';
import { RootState } from '@/redux/store';
import { useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';

import SidePanel from '@/components/common/SidePanel';
import { TripInfo, TripSchedule } from '@/components/schedule';
import { useState } from 'react';
import { getRegionCenterLat, getRegionCenterLng } from '@/utils/map';
import instance from '@/queries/axiosinstance';

interface PlanDetailPageProps {}

const PlanDetailPage = ({}: PlanDetailPageProps) => {
  const { id } = useParams();
  const { data, isLoading, error } = usePlanQuery(id!);
  const { schedules } = useSelector((state: RootState) => state.schedule);

  const [mapLevel, setMapLevel] = useState(8);

  useState(() => {
    instance
      .get(`/api/schedules/${id}/share?id=5&email=test@naver.com`)
      .then((res) => console.log(res));
  });

  return (
    <div className="relative h-full w-full">
      {isLoading ? (
        <div>로딩중</div> // TODO fallback 넣기
      ) : error ? (
        <div>에러</div> // TODO fallback 넣기
      ) : (
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
          <MenuButtons />

          <SidePanel position={'right'}>
            <TripInfo
              title={data?.title!}
              region={data?.region!}
              startDate={data?.startDate!}
              endDate={data?.endDate!}
            />
            <TripSchedule startDate={data?.startDate!} places={schedules} />

            {/* Side Panel 좌측 바깥 */}
            <Link to={`/plan/map/${id}`}>
              <Button variant={'primary'} className="absolute -left-1/2 top-6" onClick={() => {}}>
                일정 수정하기
              </Button>
            </Link>

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
      )}
    </div>
  );
};

export default PlanDetailPage;
