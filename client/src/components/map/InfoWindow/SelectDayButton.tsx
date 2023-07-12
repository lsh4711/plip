import { FiChevronDown } from '@react-icons/all-files/fi/FiChevronDown';
import { FiChevronUp } from '@react-icons/all-files/fi/FiChevronUp';
import { useState } from 'react';

import { Button } from '@/components';

const SelectDayButton = () => {
  const [showSelect, setShowSelect] = useState(false);
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
        <div className="absolute mt-1 flex max-h-32 w-32 flex-col gap-2 overflow-y-scroll rounded-lg border-[1px] bg-white bg-scroll px-4 py-2 drop-shadow-lg">
          {['Day1', 'Day2', 'Day3', 'Day4', 'Day5'].map((day, idx) => (
            <div key={idx} className="cursor-pointer text-sm hover:text-blue-300">
              {day}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default SelectDayButton;
