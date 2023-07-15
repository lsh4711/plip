import { useState } from 'react';
import { useParams } from 'react-router-dom';

import { Button } from '@/components';
import SidePanel from '@/components/common/SidePanel';
import TripInfo from '@/components/common/TripInfo';
import TripSchedule from '@/components/common/TripSchedule';
import WriteModal from '@/components/common/WriteModal';
import { Map, MenuButtons, SearchTools, ZoomButtons } from '@/components/map';
import useModal from '@/hooks/useModal';
import { usePlanQuery } from '@/queries/plan';
import getRegionCenterLat from '@/utils/map/getRegionCenterLat';
import getRegionCenterLng from '@/utils/map/getRegionCenterLng';
import useToast from '@/hooks/useToast';

const PlanMapPage = () => {
  const { id } = useParams();
  const { data, isLoading, error } = usePlanQuery(id!);

  const [schedule, setSchedule] = useState(data?.places);

  const [openModal] = useModal();
  const toast = useToast();
  const [mapLevel, setMapLevel] = useState(8);

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
            centerLat={getRegionCenterLat(data?.region!)}
            centerLng={getRegionCenterLng(data?.region!)}
            mapLevel={mapLevel}
            setMapLevel={setMapLevel}
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
            <TripSchedule startDate={data?.startDate!} places={schedule!} />

            {/* Side Panel 좌측 바깥 */}
            <Button
              variant={'primary'}
              className="absolute -left-1/2 top-6"
              onClick={() => {
                toast({ content: 'dffff', type: 'success' });
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
                setMapLevel(mapLevel - 1);
              }}
              onClickZoomOut={() => {
                setMapLevel(mapLevel + 1);
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
