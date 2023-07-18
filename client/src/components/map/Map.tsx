import { SetStateAction, useState } from 'react';
import { CustomOverlayMap, Map as KakaoMap, MapMarker, Polyline } from 'react-kakao-maps-sdk';
import { useSelector } from 'react-redux';

import { InfoWindow } from '@/components/map/InfoWindow';
import { COLORS } from '@/datas/map-constants';
import { RootState } from '@/redux/store';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import { CategoryGroupCode } from '@/types/mapApi/place-types';

interface mapProps {
  type: 'scheduling' | 'recording';
  centerLat: number;
  centerLng: number;
  mapLevel: number;
  setCenterPosition?: React.Dispatch<SetStateAction<{ lat: number; lng: number }>>; // 임시 optional
  setMapLevel: React.Dispatch<SetStateAction<number>>;
  schedules: ScheduledPlaceBase[][];
  showPolyline?: boolean;
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
}: mapProps) => {
  const [selectedPlace, setSelectedPlace] = useState<ScheduledPlaceBase | null>(null);
  const { results } = useSelector((state: RootState) => state.searchPlace);

  const onClickMarker = (place: ScheduledPlaceBase) => {
    if (type === 'scheduling') {
      setSelectedPlace(place);
      if (setCenterPosition) {
        // 포커스된 마커 위치를 가운데로 옮기기 위함
        setCenterPosition({ lat: parseFloat(place.latitude), lng: parseFloat(place.longitude) });
      }
    }
    if (type === 'recording') {
      // TODO : open editor modal
    }
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
      isPanto
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
              phone: result.phone,
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
          <InfoWindow
            id={selectedPlace.apiId}
            placeName={selectedPlace.name}
            address={selectedPlace.address}
            latitude={selectedPlace.latitude}
            longitude={selectedPlace.longitude}
            category={selectedPlace.category}
            phone={selectedPlace.phone}
            isBookmarked={selectedPlace.bookmark}
            onClickClose={() => setSelectedPlace(null)}
            className="absolute bottom-8 -translate-x-1/2"
          />
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

// const [isMarkerVisble, setIsMarkerVisible] = useState(true);
// const [selectedRegion, setSelectedRegion] = useState(regionInfos['seoul']); // 타입 해결해주세요 길종늼
// const [markers, setMarkers] = useState<PositionType[]>([]);

// const onDeleteClicedkMarker = (index: number) => {
//   const filtered = markers.filter((marker, markersIndex) => markersIndex !== index);

//   setMarkers(filtered);
// };

// useEffect(() => {
//   if (mapLevel >= 10) setIsMarkerVisible(false);
//   else setIsMarkerVisible(true);
// }, [mapLevel]);

/* <Map
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
          </Map> */
