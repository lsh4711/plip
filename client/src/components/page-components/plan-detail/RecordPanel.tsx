import { lazy, useEffect } from 'react';
import Record from './Record';
import NoRecord from './NoRecord';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';

// const Record = lazy(() => import('@/components/page-components/plan-detail/Record'));
// const NoRecord = lazy(() => import('@/components/page-components/plan-detail/NoRecord'));

type Props = {
  refetch: () => void;
};

const RecordPanel = ({ refetch }: Props) => {
  const { selectedPlace } = useSelector((state: RootState) => state.place);
  const { records } = useSelector((state: RootState) => state.records);

  return (
    <>
      <div className="fixed left-6 top-[5%] z-50 flex h-[80%] w-[300px] flex-col rounded-lg bg-white text-sm opacity-80 drop-shadow-2xl hover:opacity-100 2xl:w-[340px]">
        {records[selectedPlace?.schedulePlaceId!] &&
        records[selectedPlace?.schedulePlaceId!].length > 0 ? (
          <>
            <Record recordRefetch={refetch} />
          </>
        ) : (
          <NoRecord refetch={refetch} />
        )}
      </div>
    </>
  );
};

export default RecordPanel;
