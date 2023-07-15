import { SetStateAction } from 'react';

import { Map as KakaoMap } from 'react-kakao-maps-sdk';
// import { Map, MapMarker, MarkerClusterer, Polyline } from 'react-kakao-maps-sdk';

interface mapProps {
  centerLat: number;
  centerLng: number;
  mapLevel: number;
  setMapLevel: React.Dispatch<SetStateAction<number>>;
}

const Map = ({ centerLat, centerLng, mapLevel, setMapLevel }: mapProps) => {
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
    />
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
