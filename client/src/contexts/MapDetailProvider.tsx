import { Record } from '@/types/api/records-types';
import { Schedule } from '@/types/api/schedules-types';
import { captureRejectionSymbol } from 'events';
import { ReactNode, useContext, useEffect, useState } from 'react';
import { createContext } from 'react';

type Props = {
  children: ReactNode;
};

type ContextProps = {
  scheduleInfo: Schedule;
  setScheduleInfo: React.Dispatch<React.SetStateAction<Schedule>>;
};

const initScheduleInfo = {} as Schedule;

const MapDetailContext = createContext<ContextProps>({
  scheduleInfo: initScheduleInfo,
  setScheduleInfo: () => {},
});

export const useMapDetailContext = () => {
  return useContext(MapDetailContext);
};

const MapDetailProvider = ({ children }: Props) => {
  // 현재 일정의 정보
  const [scheduleInfo, setScheduleInfo] = useState<Schedule>(initScheduleInfo);

  return (
    <MapDetailContext.Provider
      value={{
        scheduleInfo,

        setScheduleInfo,
      }}
    >
      {children}
    </MapDetailContext.Provider>
  );
};

export default MapDetailProvider;
