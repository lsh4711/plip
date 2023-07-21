import { useState } from 'react';

import { HeadingParagraph } from '@/components';
import { Map } from '@/components/map';
import useAuthRedirect from '@/hooks/useAuthRedirect';

const FootPrintPage = () => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const [mapLevel, setMapLevel] = useState(12);
  const center = { lat: 35.81905, lng: 127.8733 }; // 구글어스 대한민국

  return (
    <div className="flex h-full flex-col">
      <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-4">
        나의 발자취
      </HeadingParagraph>
      <Map
        type="recording"
        centerLat={center.lat}
        centerLng={center.lng}
        mapLevel={mapLevel}
        setMapLevel={setMapLevel}
        schedules={[[]]}
        className="h-full w-full"
      />
    </div>
  );
};

export default FootPrintPage;
