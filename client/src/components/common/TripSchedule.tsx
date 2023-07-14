import dayjs from 'dayjs';
import { useState } from 'react';

import Button from '@/components/atom/Button';
import ScheduleAccordion from './ScheduleAccordion';
import { GetScheduleResponse } from '@/types/api/schedules-types';

function TripSchedule({ startDate, places }: Pick<GetScheduleResponse, 'startDate' | 'places'>) {
  const [isEditMode, setIsEditMode] = useState(false);

  return (
    <div className="flex h-full flex-col gap-4 overflow-scroll p-6">
      {isEditMode ? (
        <Button
          variant={'primary'}
          size={'full'}
          hovercolor={'default'}
          hoveropacity={'active'}
          onClick={() => setIsEditMode(!isEditMode)}
          className="text-xs font-medium"
        >
          편집 완료하기
        </Button>
      ) : (
        <Button
          variant={'optional'}
          size={'full'}
          hovercolor={'default'}
          hoveropacity={'active'}
          onClick={() => setIsEditMode(!isEditMode)}
          className="text-xs font-medium"
        >
          일정 편집하기
        </Button>
      )}
      {places.map((place, idx: number) => (
        <ScheduleAccordion
          key={idx}
          title={`Day ${idx + 1}`}
          date={dayjs(startDate).add(idx, 'day').toDate()}
          contents={place}
          isEditMode={isEditMode}
        />
      ))}
    </div>
  );
}

export default TripSchedule;
