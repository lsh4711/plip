import { Record } from '@/types/api/records-types';
import { ReactNode, useContext, useEffect, useState } from 'react';
import { createContext } from 'react';

type Props = {
  children: ReactNode;
};

type RecordMapProps = {
  [key: number]: Record[];
};

type ContextProps = {
  placeId: number;
  currentRecord: Record | undefined;
  setPlaceId: React.Dispatch<React.SetStateAction<number>>;
  setRecords: React.Dispatch<React.SetStateAction<RecordMapProps | undefined>>;
};

const MapDetailContext = createContext<ContextProps>({
  placeId: 0,
  currentRecord: undefined,
  setPlaceId: () => {},
  setRecords: () => {},
});

export const useMapDetailContext = () => {
  return useContext(MapDetailContext);
};

const MapDetailProvider = ({ children }: Props) => {
  // 마우스 호버할 때 해당 지역의 아이디 - schedule-place-id
  const [placeId, setPlaceId] = useState(0);

  // 일정의 일지들 저장
  const [records, setRecords] = useState<RecordMapProps>();

  // 현재 일지 정보
  const [currentRecord, setCurrentRecord] = useState<Record>();

  useEffect(() => {
    if (records && placeId > 0) {
      setCurrentRecord(records[placeId][0]);
    } else {
      setCurrentRecord(undefined);
    }
  }, [placeId]);

  return (
    <MapDetailContext.Provider value={{ placeId, currentRecord, setPlaceId, setRecords }}>
      {children}
    </MapDetailContext.Provider>
  );
};

export default MapDetailProvider;
