import { useMapDetailContext } from '@/contexts/MapDetailProvider';
import { lazy, useEffect, useState } from 'react';

const Record = lazy(() => import('@/components/page-components/plan-detail/Record'));
const NoRecord = lazy(() => import('@/components/page-components/plan-detail/NoRecord'));

const RecordPanel = () => {
  const { placeId, currentRecord, scheduleInfo } = useMapDetailContext();
  // const [show, setShow] = useState(false);

  useEffect(() => {
    console.log(placeId);
    console.log(currentRecord);
    // setShow(false);
    // setTimeout(() => setShow(true), 3000);
  }, [placeId]);

  return (
    <>
      {placeId !== -1 ? (
        <div className="fixed left-6 top-[5%] z-50 flex h-[80%] w-[300px] flex-col rounded-lg bg-white text-sm opacity-80 drop-shadow-2xl hover:opacity-100 2xl:w-[340px]">
          {currentRecord ? (
            <Record content={currentRecord} />
          ) : (
            <NoRecord id={placeId} scheduleInfo={scheduleInfo} />
          )}
        </div>
      ) : (
        ''
      )}
    </>
  );
};

export default RecordPanel;
