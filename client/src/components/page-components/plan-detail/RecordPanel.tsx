import Record from '@/components/page-components/plan-detail/Record';
import NoRecord from '@/components/page-components/plan-detail/NoRecord';
import { useMapDetailContext } from '@/contexts/MapDetailProvider';

const RecordPanel = () => {
  const { placeId, currentRecord } = useMapDetailContext();

  return (
    <div className="fixed left-6 top-[5%] z-50 flex h-[80%] flex-col rounded-lg bg-white opacity-80 drop-shadow-2xl hover:opacity-100 2xl:h-[800px] 2xl:w-[340px]">
      {currentRecord ? <Record content={currentRecord} /> : <NoRecord id={placeId} />}
    </div>
  );
};

export default RecordPanel;
