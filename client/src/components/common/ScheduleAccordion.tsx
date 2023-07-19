import * as Accordion from '@radix-ui/react-accordion';
import { FiChevronDown } from '@react-icons/all-files/fi/FiChevronDown';
import { MdRemoveCircleOutline } from '@react-icons/all-files/md/MdRemoveCircleOutline';
import { VscMenu } from '@react-icons/all-files/vsc/VscMenu';
import type { Identifier, XYCoord } from 'dnd-core';
import update from 'immutability-helper';
import { DndProvider, useDrag, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { useCallback, useRef, useState } from 'react';

import { COLORS } from '@/datas/map-constants';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import { getFormatDateString } from '@/utils/date';

type Props = {
  order: number;
  title: string;
  date: Date;
  contents: ScheduledPlaceBase[];
  isEditMode: boolean;
};

interface DndAccordionContent {
  id: number | string;
  name: string;
  order: number;
  index: number;
  moveItem: (dragIndex: number, hoverIndex: number) => void;
}

interface DndContainer {
  contents: ScheduledPlaceBase[];
  order: number;
}

function DndContainer({ contents, order }: DndContainer) {
  const [items, setItems] = useState(contents);
  console.log(items);

  const moveItem = useCallback((dragIndex: number, hoverIndex: number) => {
    setItems((prevItems) =>
      update(prevItems, {
        $splice: [
          [dragIndex, 1],
          [hoverIndex, 0, prevItems[dragIndex]],
        ],
      })
    );
  }, []);

  const renderItem = useCallback(
    (name: string, apiId: number, idx: number) => (
      <DndAccordionContent
        key={`${apiId}`}
        id={`${apiId}`} // TODO Check
        index={idx}
        name={name}
        order={order}
        moveItem={moveItem}
      />
    ),
    []
  );

  return <div>{items.map(({ name, apiId }, idx) => renderItem(name, apiId, idx))}</div>;
}

interface DragItem {
  index: number;
  id: string;
  type: string;
}

function DndAccordionContent({ id, index, order, name, moveItem }: DndAccordionContent) {
  const ref = useRef<any>(null);
  const [{ handlerId }, drop] = useDrop<DragItem, void, { handlerId: Identifier | null }>({
    accept: 'content',
    collect(monitor) {
      return {
        handlerId: monitor.getHandlerId(),
      };
    },
    hover(item: DragItem, monitor) {
      if (!ref.current) {
        return;
      }
      const dragIndex = item.index;
      const hoverIndex = index;

      if (dragIndex === hoverIndex) {
        return;
      }

      const hoverBoundingRect = ref.current?.getBoundingClientRect();
      const hoverMiddleY = (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;
      const clientOffset = monitor.getClientOffset();
      const hoverClientY = (clientOffset as XYCoord).y - hoverBoundingRect.top;

      if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
        return;
      }

      if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
        return;
      }

      moveItem(dragIndex, hoverIndex);

      item.index = hoverIndex;
    },
  });

  const [{ isDragging }, drag] = useDrag({
    type: 'content',
    item: () => {
      return { id, index };
    },
    collect: (monitor: any) => ({
      isDragging: monitor.isDragging(),
    }),
  });

  const opacity = isDragging ? 0 : 1;
  drag(drop(ref));

  return (
    <Accordion.Content
      key={id}
      className="AccordionContent cursor-move select-none overflow-hidden border-t-[1px]"
      ref={ref}
      style={{ opacity }}
      data-handler-id={handlerId}
    >
      <div className="relative flex items-center justify-between px-4 py-3">
        <button className="absolute -translate-x-1">
          <MdRemoveCircleOutline color="#FF3535" />
        </button>
        <div className="mx-4 flex text-xs 2xl:text-sm">{name}</div>
        <VscMenu color="#bbb" className="w-3 2xl:w-4" />
      </div>
    </Accordion.Content>
  );
}

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
          isEditMode ? (
            <>
              <DndProvider backend={HTML5Backend}>
                <DndContainer contents={contents} order={order} />
              </DndProvider>
            </>
          ) : (
            contents.map(({ name, apiId }) => (
              <Accordion.Content
                key={apiId}
                className="AccordionContent overflow-hidden border-t-[1px]"
              >
                <div className="relative flex items-center justify-between px-4 py-3">
                  <div className="absolute bottom-0 top-0 h-full border-[0.68px] border-dashed border-[#bbb]"></div>
                  <div
                    className="absolute top-1/2 h-[7.5px] w-[7.5px] -translate-x-[3.5px] -translate-y-1/2 rounded-full"
                    style={{ backgroundColor: COLORS[order % COLORS.length] }}
                  ></div>
                  <div className="mx-4 flex text-xs 2xl:text-sm">{name}</div>
                </div>
              </Accordion.Content>
            ))
          )
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
