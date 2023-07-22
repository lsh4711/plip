import { useState } from 'react';

import { HeadingParagraph } from '@/components';
import { Map } from '@/components/map';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import useVisitedPlacesQuery from '@/queries/mytrip/useVisitedPlacesQuery';
import { GetScheduleResponse, ScheduledPlaceBase } from '@/types/api/schedules-types';

const FootPrintPage = () => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;
  const { data, isError } = useVisitedPlacesQuery();
  const places: ScheduledPlaceBase[][] = data.map((item: GetScheduleResponse) =>
    item.places.flat()
  );

  const [mapLevel, setMapLevel] = useState(13);
  const center = { lat: 35.81905, lng: 127.8733 }; // 구글어스 대한민국

  return (
    <div className="flex h-full flex-col">
      <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-4">
        나의 발자취
      </HeadingParagraph>
      <div className="relative h-full w-full">
        <Map
          type="clustering"
          centerLat={center.lat}
          centerLng={center.lng}
          mapLevel={mapLevel}
          setMapLevel={setMapLevel}
          schedules={places}
          className="h-full w-full"
        />
        {!places.length && (
          <div className="absolute bottom-0 left-0 right-0 top-0 z-10 flex items-center justify-center bg-white/70 text-lg font-bold text-[#4568DC]">
            아직 다녀온 여행이 없어요. 여행을 떠나볼까요?
          </div>
        )}
      </div>
    </div>
  );
};

export default FootPrintPage;
