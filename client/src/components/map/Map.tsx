import { Map as KakaoMap } from 'react-kakao-maps-sdk';

interface mapProps {}

const Map = ({}: mapProps) => {
  return (
    <KakaoMap // 지도를 표시할 Container
      center={{
        // 지도의 중심좌표
        lat: 33.450701,
        lng: 126.570667,
      }}
      level={3} // 지도의 확대 레벨
      className="h-screen w-screen"
    />
  );
};

export default Map;
