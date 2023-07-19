import * as Accordion from '@radix-ui/react-accordion';
import { FiChevronDown } from '@react-icons/all-files/fi/FiChevronDown';

import { BlankContent, Content, DndContents } from '@/components/schedule/accordion-content';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import { getFormatDateString } from '@/utils/date';

type ScheduleAccordion = {
  dayNumber: number;
  title: string;
  date: Date;
  contents: ScheduledPlaceBase[];
  isEditMode: boolean;
};

function ScheduleAccordion({ dayNumber, title, date, contents, isEditMode }: ScheduleAccordion) {
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
          isEditMode ? (
            <DndContents contents={contents} dayNumber={dayNumber} />
          ) : (
            contents.map(({ name, apiId }) => (
              <Content key={apiId} name={name} dayNumber={dayNumber} />
            ))
          )
        ) : (
          <BlankContent />
        )}
      </Accordion.Item>
    </Accordion.Root>
  );
}

export default ScheduleAccordion;
