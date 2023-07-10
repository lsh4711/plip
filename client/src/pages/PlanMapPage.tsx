import { Button } from '@/components';
import { useEffect, useState } from 'react';
import { Map, MapMarker, MarkerClusterer, Polyline } from 'react-kakao-maps-sdk';
import { PositionType } from '@/datas/regions';
import { regionInfos } from '@/datas/regions';
import { useParams } from 'react-router-dom';

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
  const { region } = useParams();

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

  // console.log(regionInfos[region]);

  const [currentPosition, setCurrentPosition] = useState<PositionType>({
    isLoad: false,
    lat: 0,
    lng: 0,
  });

  const [markers, setMarkers] = useState<PositionType[]>([]);
  console.log(currentPosition.isLoad);
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

  useEffect(() => {
    if (mapLevel >= 10) setIsMarkerVisible(false);
    else setIsMarkerVisible(true);
  }, [mapLevel]);

  return (
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
            {markers.map((pos) => {
              console.log(pos.lat, pos.lng);
              return (
                <MapMarker
                  key={`${pos.lat}-${pos.lng}`}
                  position={{
                    lat: pos.lat,
                    lng: pos.lng,
                  }}
                />
              );
            })}
          </MarkerClusterer>
        </Map>
      )}
      <Button variant={'primary'} className="absolute right-10 top-5 z-50">
        저장하기
      </Button>
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
