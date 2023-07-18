import { SetStateAction, useState } from 'react';
import { CustomOverlayMap, Map as KakaoMap, MapMarker, Polyline } from 'react-kakao-maps-sdk';
import { useSelector } from 'react-redux';

import { InfoWindow } from '@/components/map/InfoWindow';
import { COLORS } from '@/datas/map-constants';
import { RootState } from '@/redux/store';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import { CategoryGroupCode } from '@/types/mapApi/place-types';

import WriteModal from '../common/WriteModal';
import useModal from '@/hooks/useModal';
import RecordOverray from './RecordOverray';

interface mapProps {
  type: 'scheduling' | 'recording';
  centerLat: number;
  centerLng: number;
  mapLevel: number;
  setMapLevel: React.Dispatch<SetStateAction<number>>;
  schedules: ScheduledPlaceBase[][];
  showPolyline?: boolean;
}

const Map = ({
  type,
  centerLat,
  centerLng,
  mapLevel,
  setMapLevel,
  schedules,
  showPolyline = false,
}: mapProps) => {
  const [selectedPlace, setSelectedPlace] = useState<ScheduledPlaceBase | null>(null);
  const { results } = useSelector((state: RootState) => state.searchPlace);
  const [openModal] = useModal();

  const openWriteDiaryModal = (schedulePlaceId: number) => {
    openModal(({ isOpen, close }) => (
      <WriteModal id={schedulePlaceId} type={'default'} isOpen={isOpen} onClose={close} />
    ));
  };

  const onClickMarker = (place: ScheduledPlaceBase) => {
    if (type === 'scheduling') {
      setSelectedPlace(place);
    }
    if (type === 'recording') {
      // TODO : open editor modal
      console.log(place);
      openWriteDiaryModal(place.schedulePlaceId);
    }
  };

  const onHoverMarker = (place: ScheduledPlaceBase) => {
    setSelectedPlace(place);
  };

  return (
    <KakaoMap // 지도를 표시할 Container
      center={{
        // 지도의 중심좌표
        lat: centerLat,
        lng: centerLng,
      }}
      level={mapLevel} // 지도의 확대 레벨
      className="h-screen w-screen"
      onZoomChanged={(map) => setMapLevel(map.getLevel())}
      zoomable={!selectedPlace}
    >
      {schedules.map((marker, idx) =>
        marker.map((place) => (
          <MapMarker
            key={place.apiId}
            position={{
              lat: parseFloat(place.latitude),
              lng: parseFloat(place.longitude),
            }}
            image={{
              src: `/marker/marker${idx % COLORS.length}.webp`,
              size: {
                width: 32,
                height: 32,
              },
            }}
            onClick={() => onClickMarker(place)}
            onMouseOver={() => onHoverMarker(place)}
          />
        ))
      )}

      {results.map((result, idx) => (
        <MapMarker
          key={result.id}
          position={{
            lat: parseFloat(result.y),
            lng: parseFloat(result.x),
          }}
          onClick={() =>
            onClickMarker({
              apiId: parseInt(result.id),
              name: result.place_name,
              address: result.address_name,
              latitude: result.y,
              longitude: result.x,
              category: result.category_group_code as CategoryGroupCode,
              bookmark: false,
            })
          }
          image={{
            src: 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
            size: {
              width: 48,
              height: 48,
            },
            options: {
              spriteSize: {
                width: 36,
                height: 691,
              },
              spriteOrigin: {
                x: 0,
                y: idx * 46,
              },
              offset: {
                x: 13,
                y: 37,
              },
            },
          }}
        />
      ))}

      {selectedPlace && (
        <CustomOverlayMap
          position={{
            lat: parseFloat(selectedPlace.latitude),
            lng: parseFloat(selectedPlace.longitude),
          }}
          clickable={true}
          zIndex={50}
        >
          {type === 'scheduling' ? (
            <InfoWindow
              id={selectedPlace.apiId}
              placeName={selectedPlace.name}
              address={selectedPlace.address}
              latitude={selectedPlace.latitude}
              longitude={selectedPlace.longitude}
              category={selectedPlace.category}
              isBookmarked={selectedPlace.bookmark}
              onClickClose={() => setSelectedPlace(null)}
              className="absolute bottom-8 -translate-x-1/2"
            />
          ) : (
            <RecordOverray onClickClose={() => setSelectedPlace(null)} />
          )}
        </CustomOverlayMap>
      )}

      {showPolyline &&
        schedules.map((schedule, idx) => (
          <Polyline
            key={idx}
            path={[
              ...schedule.map((place) => ({
                lat: parseFloat(place.latitude),
                lng: parseFloat(place.longitude),
              })),
            ]}
            strokeOpacity={0.7}
            strokeWeight={14 - mapLevel}
            strokeColor={COLORS[idx % COLORS.length]}
          />
        ))}
    </KakaoMap>
  );
};

export default Map;
