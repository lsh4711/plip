import { useEffect, useState } from 'react';
import { Map, MapMarker, MarkerClusterer, Polyline } from 'react-kakao-maps-sdk';
import { useParams } from 'react-router-dom';

import { Button } from '@/components';
import SidePanel from '@/components/common/SidePanel';
import TripInfo from '@/components/common/TripInfo';
import TripSchedule from '@/components/common/TripSchedule';
import WriteModal from '@/components/common/WriteModal';
import { MenuButtons, SearchTools, ZoomButtons } from '@/components/map';
import { PositionType, regionInfos, regions } from '@/datas/regions';
import useModal from '@/hooks/useModal';

export type ResponseData = {
  title: string | null;
  region: (typeof regions)[number];
  startDate: Date;
  endDate: Date;
  places: any;
};

const PlanMapPage = () => {
  const { region } = useParams();
  const [openModal] = useModal();

  const [mapLevel, setMapLevel] = useState(8);
  const [isMarkerVisble, setIsMarkerVisible] = useState(true);
  const [selectedRegion, setSelectedRegion] = useState(regionInfos['seoul']); // 타입 해결해주세요 길종늼

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

  // TODO 일지 작성 페이지로 이동 필요
  const openWriteDiaryModal = () => {
    openModal(({ isOpen, close }) => (
      <WriteModal type={'default'} isOpen={isOpen} onClose={close} />
    ));
  };

  // console.log(regionInfos[region]);

  const [currentPosition, setCurrentPosition] = useState<PositionType>({
    isLoad: false,
    lat: 0,
    lng: 0,
  });

  const [markers, setMarkers] = useState<PositionType[]>([]);

  const onDeleteClicedkMarker = (index: number) => {
    const filtered = markers.filter((marker, markersIndex) => markersIndex !== index);

    setMarkers(filtered);
  };

  useEffect(() => {
    const { lat, lng } = selectedRegion.coords;

    setCurrentPosition({
      isLoad: true,
      lat: lat,
      lng: lng,
    });
  }, []);

  useEffect(() => {
    if (mapLevel >= 10) setIsMarkerVisible(false);
    else setIsMarkerVisible(true);
  }, [mapLevel]);

  return (
    <>
      <div className="relative h-full w-full">
        {currentPosition.isLoad && (
          <Map
            center={currentPosition}
            className="h-full w-full"
            level={mapLevel}
            onClick={(_t, mouseEvent) =>
              setMarkers([
                ...markers,
                {
                  lat: mouseEvent.latLng.getLat(),
                  lng: mouseEvent.latLng.getLng(),
                },
              ])
            }
            onZoomChanged={(map) => setMapLevel(map.getLevel())}
          >
            {isMarkerVisble && markers.map((marker) => <MapMarker position={marker}></MapMarker>)}

            <Polyline
              path={[[...markers]]}
              strokeWeight={5} // 선의 두께 입니다
              strokeColor={'#FFAE00'} // 선의 색깔입니다
              strokeOpacity={0.7} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
              strokeStyle={'solid'} // 선의 스타일입니다
            />

            <MarkerClusterer
              averageCenter={true} // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
              minLevel={10} // 클러스터 할 최소 지도 레벨
            >
              {markers.map((pos, index) => {
                console.log(pos.lat, pos.lng);
                return (
                  <MapMarker
                    key={`${pos.lat}-${pos.lng}`}
                    position={{
                      lat: pos.lat,
                      lng: pos.lng,
                    }}
                    onClick={() => onDeleteClicedkMarker(index)}
                  />
                );
              })}
            </MarkerClusterer>
          </Map>
        )}
        <SearchTools />
        <MenuButtons />
        <SidePanel position={'right'}>
          <TripInfo
            title={responseData.title}
            region={responseData.region}
            startDate={responseData.startDate}
            endDate={responseData.endDate}
          />
          <TripSchedule startDate={responseData.startDate} places={responseData.places} />
          {/* Side Panel 좌측 바깥 */}
          <Button variant={'primary'} className="absolute -left-1/2 top-6">
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
            onClickZoomIn={() => {}}
            onClickZoomOut={() => {}}
            className={'absolute -left-16 bottom-6 z-50'}
          />
        </SidePanel>
      </div>
    </>
  );
};

export default PlanMapPage;
