import { useCallback, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate, useParams, useBeforeUnload } from 'react-router-dom';

import { Button } from '@/components';
import Confirm from '@/components/common/Confirm';
import SidePanel from '@/components/common/SidePanel';
import TripInfo from '@/components/common/TripInfo';
import TripSchedule from '@/components/common/TripSchedule';
import { Map, MenuButtons, SearchTools, ZoomButtons } from '@/components/map';
import useDebounce from '@/hooks/useDebounce';
import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';
import { useEditPlanMutation, usePlanQuery } from '@/queries/plan';
import { setIsStale } from '@/redux/slices/scheduleSlice';
import { RootState } from '@/redux/store';
import { getRegionCenterLat, getRegionCenterLng } from '@/utils/map';
import { setSearchPlaceResults } from '@/redux/slices/placeSlice';
import LoadingPage from './LoadingPage';

const PlanMapPage = () => {
  const { id } = useParams();
  const { data, isLoading, error } = usePlanQuery(id!);
  const { isStale, schedules } = useSelector((state: RootState) => state.schedule);

  const navigate = useNavigate();
  const toast = useToast();
  const [openModal] = useModal();
  const [mapLevel, setMapLevel] = useState(8);
  const [centerPosition, setCenterPosition] = useState({
    lat: getRegionCenterLat(data?.region!),
    lng: getRegionCenterLng(data?.region!),
  });

  const dispatch = useDispatch();
  const mutation = useEditPlanMutation(id!);
  const patchSchedule = (noticeType: 'toast' | 'confirm') =>
    mutation
      .mutateAsync({
        id: id!,
        places: schedules,
      })
      .then((res) => {
        dispatch(setIsStale(false));
        if (noticeType === 'confirm') {
          openModal(({ isOpen, close }) => (
            <Confirm
              type="default"
              title="일정 저장 완료!"
              content="일정 목록으로 이동할까요?"
              primaryLabel="일정 목록으로 이동하기"
              secondaryLabel="이어서 작성하기"
              onClickPrimaryButton={() => {
                navigate('/mypage');
                close();
              }}
              onClickSecondaryButton={close}
              isOpen={isOpen}
              onClose={close}
            />
          ));
        }
        if (noticeType === 'toast') {
          toast({
            content: '자동 저장되었습니다.',
            type: 'success',
          });
        }
      });
  const autoPatchSchedule = useDebounce(() => patchSchedule('toast'), 1000 * 15);

  useEffect(() => {
    if (isStale) {
      autoPatchSchedule();
    }
  });

  useBeforeUnload(
    useCallback(() => {
      dispatch(setSearchPlaceResults([]));
    }, [])
  );

  return (
    <div className="relative h-full w-full">
      {isLoading ? (
        <LoadingPage />
      ) : error ? (
        <div>에러</div> // TODO fallback 넣기
      ) : (
        <>
          <Map
            type="scheduling"
            centerLat={centerPosition.lat}
            centerLng={centerPosition.lng}
            setCenterPosition={setCenterPosition}
            mapLevel={mapLevel}
            setMapLevel={setMapLevel}
            schedules={schedules}
            showPolyline
          />

          <SearchTools currentLng={centerPosition.lng} currentLat={centerPosition.lat} />
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
                patchSchedule('confirm');
              }}
            >
              일정 저장하기
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
