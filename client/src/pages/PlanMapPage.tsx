import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';

import { Button } from '@/components';
import { Map, MenuButtons, SearchTools, ZoomButtons } from '@/components/map';
import { useEditPlanMutation, usePlanQuery } from '@/queries/plan';
import { RootState } from '@/redux/store';
import { getRegionCenterLat, getRegionCenterLng } from '@/utils/map';

import SidePanel from '@/components/common/SidePanel';
import TripInfo from '@/components/common/TripInfo';
import TripSchedule from '@/components/common/TripSchedule';
import WriteModal from '@/components/common/WriteModal';
import useDebounce from '@/hooks/useDebounce';
import useModal from '@/hooks/useModal';

const PlanMapPage = () => {
  const { id } = useParams();
  const { data, isLoading, error } = usePlanQuery(id!);
  const { schedules } = useSelector((state: RootState) => state.schedule);

  const [openModal] = useModal();
  const [mapLevel, setMapLevel] = useState(8);

  const mutation = useEditPlanMutation(id!);
  const patchSchedule = () =>
    mutation.mutate({
      id: id!,
      places: schedules,
    });
  const autoPatchSchedule = useDebounce(patchSchedule, 1000 * 10);

  useEffect(() => {
    if (JSON.stringify(data?.places!) !== JSON.stringify(schedules)) {
      autoPatchSchedule();
    }
  }, [schedules]);

  // TODO 일지 작성 페이지로 이동 필요
  const openWriteDiaryModal = () => {
    openModal(({ isOpen, close }) => (
      <WriteModal type={'default'} isOpen={isOpen} onClose={close} />
    ));
  };

  return (
    <div className="relative h-full w-full">
      {isLoading ? (
        <div>로딩중</div> // TODO fallback 넣기
      ) : error ? (
        <div>에러</div> // TODO fallback 넣기
      ) : (
        <>
          <Map
            type="scheduling"
            centerLat={getRegionCenterLat(data?.region!)}
            centerLng={getRegionCenterLng(data?.region!)}
            mapLevel={mapLevel}
            setMapLevel={setMapLevel}
            schedules={schedules}
            showPolyline
          />

          <SearchTools
            currentX={getRegionCenterLng(data?.region!)}
            currentY={getRegionCenterLat(data?.region!)}
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
            <Button
              variant={'primary'}
              className="absolute -left-1/2 top-6"
              onClick={() => {
                patchSchedule();
              }}
            >
              일정 저장하기
            </Button>
            {/* TODO 일지 작성 페이지로 이동 필요 */}
            <Button
              variant={'primary'}
              className="absolute -left-1/2 top-20"
              onClick={openWriteDiaryModal}
            >
              일지 작성하기
            </Button>
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

export default PlanMapPage;
