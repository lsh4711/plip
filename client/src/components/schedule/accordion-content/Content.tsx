import * as Accordion from '@radix-ui/react-accordion';

import { COLORS } from '@/datas/map-constants';

type Content = {
  name: string;
  dayNumber: number;
  onClick: () => void;
};

const Content = ({ name, dayNumber, onClick }: Content) => {
  return (
    <Accordion.Content
      className="AccordionContent cursor-pointer overflow-hidden border-t-[1px]"
      onClick={onClick}
    >
      <div className="relative flex items-center justify-between px-4 py-3">
        <div className="absolute bottom-0 top-0 h-full border-[0.68px] border-dashed border-[#bbb]"></div>
        <div
          className="absolute top-1/2 h-[7.5px] w-[7.5px] -translate-x-[3.5px] -translate-y-1/2 rounded-full"
          style={{ backgroundColor: COLORS[dayNumber % COLORS.length] }}
        ></div>
        <div className="mx-4 flex text-xs 2xl:text-sm">{name}</div>
      </div>
    </Accordion.Content>
  );
};

export default Content;
