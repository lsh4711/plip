import dayjs from 'dayjs';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { useMatch, useParams } from 'react-router-dom';

import Button from '@/components/atom/Button';
import { ScheduleAccordion } from '@/components/schedule';
import useToast from '@/hooks/useToast';
import { useEditPlanMutation } from '@/queries/plan';
import { RootState } from '@/redux/store';
import { ScheduledPlaceBase, Schedule } from '@/types/api/schedules-types';

type Props = {
  startDate: Schedule['startDate'];
  places: ScheduledPlaceBase[][];
};

function TripSchedule({ startDate, places }: Props) {
  const { id } = useParams();
  const { schedules } = useSelector((state: RootState) => state.schedule);
  const [isEditMode, setIsEditMode] = useState(false);
  const isPlanningPage = useMatch('/plan/map/:id');
  const [scheduleBeforeEdit, setScheduleBeforeEdit] = useState<ScheduledPlaceBase[][]>();

  const toast = useToast();
  const mutation = useEditPlanMutation(id!);

  const hasChanges = () => {
    return JSON.stringify(scheduleBeforeEdit) !== JSON.stringify(schedules);
  };

  const patchSchedule = () =>
    mutation
      .mutateAsync({
        id: id!,
        places: schedules,
      })
      .then(() => {
        toast({
          content: '저장되었습니다.',
          type: 'success',
        });
        setIsEditMode(false);
      })
      .catch((e) => {
        toast({
          content: '저장에 실패했습니다. 잠시 후에 다시 시도해 주세요.',
          type: 'warning',
        });
      });

  return (
    <div className="flex h-full flex-col gap-4 overflow-scroll p-6">
      {isPlanningPage &&
        (isEditMode ? (
          <Button
            variant={'primary'}
            size={'full'}
            hovercolor={'default'}
            hoveropacity={'active'}
            onClick={() => (hasChanges() ? patchSchedule() : setIsEditMode(false))}
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
            onClick={() => {
              setScheduleBeforeEdit(schedules);
              setIsEditMode(!isEditMode);
            }}
            className=" bg-[#e5f0ff] text-xs font-medium text-[#4568DC]"
          >
            일정 편집하기
          </Button>
        ))}
      {places.map((place, idx: number) => (
        <ScheduleAccordion
          key={idx}
          dayNumber={idx}
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
