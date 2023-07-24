import { SetStateAction, useEffect, useRef } from 'react';
import {
  CustomOverlayMap,
  Map as KakaoMap,
  MapMarker,
  MarkerClusterer,
  Polyline,
} from 'react-kakao-maps-sdk';
import { useDispatch, useSelector } from 'react-redux';

import { InfoWindow } from '@/components/map/InfoWindow';
import { useMapDetailContext } from '@/contexts/MapDetailProvider';
import { COLORS } from '@/datas/map-constants';
import useHoverTimer from '@/hooks/useHoverTimer';
import { setSelectedPlace } from '@/redux/slices/placeSlice';
import { RootState } from '@/redux/store';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import { CategoryGroupCode } from '@/types/mapApi/place-types';
import { cn } from '@/utils';
import usePreventZoom from '@/hooks/usePreventZoom';

interface mapProps {
  type: 'scheduling' | 'recording' | 'clustering';
  centerLat: number;
  centerLng: number;
  mapLevel: number;
  setCenterPosition?: React.Dispatch<SetStateAction<{ lat: number; lng: number }>>; // 임시 optional
  setMapLevel: React.Dispatch<SetStateAction<number>>;
  schedules: ScheduledPlaceBase[][];
  showPolyline?: boolean;
  className?: string;
}

const Map = ({
  type,
  centerLat,
  centerLng,
  setCenterPosition,
  mapLevel,
  setMapLevel,
  schedules,
  showPolyline = false,
  className,
}: mapProps) => {
  usePreventZoom();

  const { searchPlaceResults, selectedPlace } = useSelector((state: RootState) => state.place);
  const dispatch = useDispatch();

  const [onHandleOpen, onHandleClose] = useHoverTimer({ openDelay: 500, closeDelay: 300 });

  const hoverMarkerTimer = useRef(0);

  // const onHoverMarker = (place: ScheduledPlaceBase) => {
  //   if (type === 'recording') {
  //     hoverMarkerTimer.current = window.setTimeout(() => {
  //       onHandleOpen(() => dispatch(setSelectedPlace(place)));
  //       setPlaceId(place.schedulePlaceId!);
  //     }, 100);
  //   }
  // };

  // const onHoverOutHandler = () => {
  //   if (type === 'recording') {
  //     clearTimeout(hoverMarkerTimer.current);
  //     onHandleClose(() => dispatch(setSelectedPlace(null)));
  //   }
  // };

  useEffect(() => {
    if (setCenterPosition && selectedPlace) {
      // 포커스된 마커 위치를 가운데로 옮기기 위함
      setCenterPosition({
        lat: parseFloat(selectedPlace.latitude),
        lng: parseFloat(selectedPlace.longitude),
      });
    }
  }, [selectedPlace]);

  return (
    <KakaoMap // 지도를 표시할 Container
      center={{
        // 지도의 중심좌표
        lat: centerLat,
        lng: centerLng,
      }}
      level={mapLevel} // 지도의 확대 레벨
      className={cn(['h-screen w-screen', className])}
      isPanto
      onClick={() => dispatch(setSelectedPlace(null))}
      onDragEnd={(
        target // 검색 지점 변경 용도
      ) =>
        setCenterPosition &&
        setCenterPosition({
          lat: target.getCenter().getLat(),
          lng: target.getCenter().getLng(),
        })
      }
      onZoomChanged={(map) => setMapLevel(map.getLevel())}
      zoomable={!selectedPlace}
    >
      {type !== 'clustering' &&
        schedules.map((marker, dayNumber) =>
          marker.map((place, visitNumber) => (
            <MapMarker
              key={place.apiId}
              position={{
                lat: parseFloat(place.latitude),
                lng: parseFloat(place.longitude),
              }}
              image={{
                src: `/markers/marker${dayNumber % COLORS.length}/marker-${
                  dayNumber % COLORS.length
                }-${visitNumber + 1}.svg`,
                size: {
                  width: 32,
                  height: 32,
                },
              }}
              onClick={() => {
                dispatch(setSelectedPlace(place));
              }}
              // onMouseOver={() => onHoverMarker(place)}
              // onMouseOut={() => onHoverOutHandler()}
            />
          ))
        )}

      {!!searchPlaceResults.length &&
        searchPlaceResults.map((result, idx) => (
          <MapMarker
            key={result.id}
            position={{
              lat: parseFloat(result.y),
              lng: parseFloat(result.x),
            }}
            onClick={() =>
              dispatch(
                setSelectedPlace({
                  apiId: parseInt(result.id),
                  name: result.place_name,
                  address: result.address_name,
                  latitude: result.y,
                  longitude: result.x,
                  phone: result.phone,
                  category: result.category_group_code as CategoryGroupCode,
                  bookmark: false,
                })
              )
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
          <InfoWindow
            id={selectedPlace.apiId}
            type={type}
            placeName={selectedPlace.name}
            address={selectedPlace.address}
            latitude={selectedPlace.latitude}
            longitude={selectedPlace.longitude}
            category={selectedPlace.category}
            phone={selectedPlace.phone}
            isBookmarked={selectedPlace.bookmark}
            onClickClose={() => dispatch(setSelectedPlace(null))}
            className="absolute bottom-8 -translate-x-1/2"
          />
        </CustomOverlayMap>
      )}

      {showPolyline &&
        schedules.map((schedule, dayNumber) => (
          <Polyline
            key={dayNumber}
            path={[
              ...schedule.map((place) => ({
                lat: parseFloat(place.latitude),
                lng: parseFloat(place.longitude),
              })),
            ]}
            strokeOpacity={0.7}
            strokeWeight={14 - mapLevel}
            strokeColor={COLORS[dayNumber % COLORS.length]}
          />
        ))}

      {type === 'clustering' && (
        <MarkerClusterer
          averageCenter={true} // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
          minLevel={10} // 클러스터 할 최소 지도 레벨
        >
          {schedules.map((places) =>
            places.map((place) => (
              <MapMarker
                key={place.apiId}
                position={{
                  lat: parseFloat(place.latitude),
                  lng: parseFloat(place.longitude),
                }}
                onClick={() => dispatch(setSelectedPlace(place))}
                image={{
                  src: '/markers/marker.svg',
                  size: {
                    width: 30,
                    height: 30,
                  },
                }}
              />
            ))
          )}
        </MarkerClusterer>
      )}
    </KakaoMap>
  );
};

export default Map;
