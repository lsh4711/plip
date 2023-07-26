import { VscCalendar } from '@react-icons/all-files/vsc/VscCalendar';
import ko from 'date-fns/locale/ko';
import { useState } from 'react';
import Picker, { ReactDatePickerProps, registerLocale } from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

registerLocale('ko', ko);

interface Props extends ReactDatePickerProps {}

function DatePicker({ placeholderText, selected, onChange, minDate, maxDate }: Props) {
  const [startDate, setStartDate] = useState<Date | null>();

  return (
    <label className="flex w-full items-center rounded-lg border border-solid border-[#bbb] py-[9px] pl-[17px] text-sm md:w-52">
      <VscCalendar className="mr-2 h-6 w-6 text-[#bbb]" size={16} />
      <Picker
        dateFormat={'yyyy년 MM월 dd일'}
        placeholderText={placeholderText}
        selected={selected}
        onChange={onChange}
        shouldCloseOnSelect={false}
        minDate={minDate}
        maxDate={maxDate}
        className="bg-transparent text-[#343539]"
        locale={ko}
        disabledKeyboardNavigation
        popperPlacement="bottom"
      />
    </label>
  );
}

export default DatePicker;
