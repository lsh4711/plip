import * as Accordion from '@radix-ui/react-accordion';
import { FiChevronDown } from '@react-icons/all-files/fi/FiChevronDown';
import { MdRemoveCircleOutline } from '@react-icons/all-files/md/MdRemoveCircleOutline';
import { VscMenu } from '@react-icons/all-files/vsc/VscMenu';

import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import { getFormatDateString } from '@/utils/date';
import { COLORS } from '@/datas/map-constants';

type Props = {
  order: number;
  title: string;
  date: Date;
  contents: ScheduledPlaceBase[];
  isEditMode: boolean;
};

function ScheduleAccordion({ order, title, date, contents, isEditMode }: Props) {
  return (
    <Accordion.Root type="single" defaultValue="schedule" collapsible>
      <Accordion.Item className="overflow-hidden rounded-lg bg-[#F6F8FC]" value="schedule">
        <Accordion.Header className="flex max-h-11 items-center justify-center px-4 py-3">
          <Accordion.Trigger className="AccordionTrigger flex grow items-center justify-between">
            <div className="flex items-center gap-3">
              <span className="text-base font-bold text-[#343539] 2xl:text-base">{title}</span>
              <span className="text-xs text-[#343539]">
                {getFormatDateString(date, true, 'dot')}
              </span>
            </div>
            <FiChevronDown className="AccordionChevron" color="#bbb" />
          </Accordion.Trigger>
        </Accordion.Header>
        {contents.length ? (
          contents.map(({ name }, idx) => (
            <Accordion.Content
              // TODO : key 변경
              key={idx}
              className="AccordionContent overflow-hidden border-t-[1px]"
            >
              <div className="relative flex items-center justify-between px-4 py-3">
                {isEditMode ? (
                  <button className="absolute -translate-x-1">
                    <MdRemoveCircleOutline color="#FF3535" />
                  </button>
                ) : (
                  <>
                    <div className="absolute bottom-0 top-0 h-full border-[0.68px] border-dashed border-[#bbb]"></div>
                    <div
                      className="absolute top-1/2 h-[7.5px] w-[7.5px] -translate-x-[3.5px] -translate-y-1/2 rounded-full"
                      style={{ backgroundColor: COLORS[order % COLORS.length] }}
                    ></div>
                  </>
                )}
                <div className="mx-4 flex text-xs 2xl:text-sm">{name}</div>
                {isEditMode && <VscMenu color="#bbb" className="w-3 2xl:w-4" />}
              </div>
            </Accordion.Content>
          ))
        ) : (
          <Accordion.Content className="AccordionContent overflow-hidden border-t-[1px]">
            <div className="flex items-center justify-center px-4 py-3 text-xs text-[#bbb]">
              추가된 일정이 없습니다.
            </div>
          </Accordion.Content>
        )}
      </Accordion.Item>
    </Accordion.Root>
  );
}

export default ScheduleAccordion;
