import { FiChevronDown } from '@react-icons/all-files/fi/FiChevronDown';
import { FiChevronUp } from '@react-icons/all-files/fi/FiChevronUp';
import { useState } from 'react';

import { Button } from '@/components';
import { RootState } from '@/redux/store';
import { useSelector } from 'react-redux';

type Props = {
  addSchedule: (day: number) => void;
};

const SelectDayButton = ({ addSchedule }: Props) => {
  const [showSelect, setShowSelect] = useState(false);
  const { schedules } = useSelector((state: RootState) => state.schedule);

  return (
    <div className="relative">
      <Button
        variant={'primary'}
        size={'default'}
        className="gap-2 text-xs"
        onClick={() => setShowSelect(!showSelect)}
      >
        일정에 추가하기 {showSelect ? <FiChevronUp /> : <FiChevronDown />}
      </Button>
      {showSelect && (
        <div className="absolute right-0 mt-1 flex max-h-32 w-fit flex-col gap-2 overflow-y-scroll rounded-lg border-[1px] bg-white bg-scroll px-4 py-3 drop-shadow-lg">
          {schedules.map((_, idx) => (
            <button
              key={idx}
              className="cursor-pointer text-base hover:text-blue-300"
              onClick={() => addSchedule(idx + 1)}
            >
              {`Day ${idx + 1}`}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default SelectDayButton;
