import { MypageSideNav } from '@/components';
import { Map, ZoomButtons } from '@/components/map';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import { useState } from 'react';

const FootPrintPage = () => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const [mapLevel, setMapLevel] = useState(12);
  const center = { lat: 35.81905, lng: 127.8733 }; // 구글어스 대한민국

  return (
    <div className=" flex">
      <MypageSideNav />
      <div className="relatvie">
        <Map
          type="recording"
          centerLat={center.lat}
          centerLng={center.lng}
          mapLevel={mapLevel}
          setMapLevel={setMapLevel}
          schedules={[[]]}
        />
        <ZoomButtons
          onClickZoomIn={() => {
            setMapLevel(mapLevel - 1);
          }}
          onClickZoomOut={() => {
            setMapLevel(mapLevel + 1);
          }}
          className={'absolute bottom-10 right-10 z-50'}
        />
      </div>
    </div>
  );
};

export default FootPrintPage;
