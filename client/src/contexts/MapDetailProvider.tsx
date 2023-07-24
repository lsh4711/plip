import { Record } from '@/types/api/records-types';
import { Schedule } from '@/types/api/schedules-types';
import { captureRejectionSymbol } from 'events';
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
  scheduleInfo: Schedule;
  records: RecordMapProps;
  setPlaceId: React.Dispatch<React.SetStateAction<number>>;
  setRecords: React.Dispatch<React.SetStateAction<RecordMapProps>>;
  setScheduleInfo: React.Dispatch<React.SetStateAction<Schedule>>;
};

const initScheduleInfo = {} as Schedule;
const initRecords = {} as RecordMapProps;
// const initRecord = {} as Record;

const MapDetailContext = createContext<ContextProps>({
  placeId: 0,
  currentRecord: undefined,
  scheduleInfo: initScheduleInfo,
  records: initRecords,
  setPlaceId: () => {},
  setRecords: () => {},
  setScheduleInfo: () => {},
});

export const useMapDetailContext = () => {
  return useContext(MapDetailContext);
};

const MapDetailProvider = ({ children }: Props) => {
  // 마우스 호버할 때 해당 지역의 아이디 - schedule-place-id
  const [placeId, setPlaceId] = useState(0);

  // 현재 일정의 정보
  const [scheduleInfo, setScheduleInfo] = useState<Schedule>(initScheduleInfo);

  // 일정의 일지들 저장
  const [records, setRecords] = useState<RecordMapProps>(initRecords);

  // 현재 일지 정보
  const [currentRecord, setCurrentRecord] = useState<Record>();

  useEffect(() => {
    console.log('map detail provider');
    // if (records && placeId > 0 && records[placeId][0]) {
    //   setCurrentRecord(records[placeId][0]);
    // } else {
    //   setCurrentRecord(undefined);
    // }
  }, [placeId]);

  return (
    <MapDetailContext.Provider
      value={{
        placeId,
        currentRecord,
        scheduleInfo,
        records,
        setPlaceId,
        setRecords,
        setScheduleInfo,
      }}
    >
      {children}
    </MapDetailContext.Provider>
  );
};

export default MapDetailProvider;
